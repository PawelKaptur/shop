package com.capgemini.service.impl;


import com.capgemini.entity.ClientEntity;
import com.capgemini.entity.ProductEntity;
import com.capgemini.entity.TransactionEntity;
import com.capgemini.exception.TransactionDeniedException;
import com.capgemini.mapper.TransactionMapper;
import com.capgemini.repository.ClientRepository;
import com.capgemini.repository.ProductRepository;
import com.capgemini.repository.TransactionRepository;
import com.capgemini.service.TransactionService;
import com.capgemini.type.TransactionTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
@Transactional(readOnly = true)
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private ClientRepository clientRepository;
    private ProductRepository productRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, ClientRepository clientRepository, ProductRepository productRepository) {
        this.transactionRepository = transactionRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
    }

    @Override
    public TransactionTO findTransactionById(Long id) {
        return TransactionMapper.toTransactionTO(transactionRepository.findTransactionEntityById(id));
    }

    @Override
    @Transactional(readOnly = false)
    public TransactionTO addTransaction(TransactionTO transaction) throws TransactionDeniedException {
        TransactionEntity transactionEntity = TransactionMapper.toTransactionEntity(transaction);
        ClientEntity clientEntity = clientRepository.findClientEntityById(transaction.getClient());
        transactionEntity.setClient(clientEntity);

        List<Long> productsId = transaction.getProducts();
        List<ProductEntity> products = new LinkedList<>();

        productsId.forEach(i -> products.add(productRepository.findProductEntityById(i)));

        transactionEntity.setProducts(products);

        checkIfTransactionPossible(clientEntity, products);

        transactionRepository.save(transactionEntity);

        addTransactionToClient(clientEntity, transactionEntity);
        addTransactionToProducts(products, transactionEntity);

        return TransactionMapper.toTransactionTO(transactionEntity);
    }

    private void checkIsWeightBiggerThan25(List<ProductEntity> products) throws TransactionDeniedException {
        Double sumOfWeight = 0D;
        for(ProductEntity product: products){
            sumOfWeight += product.getWeight();
        }

        if(sumOfWeight > 25){
            throw new TransactionDeniedException();
        }
    }

    private void checkIfTransactionPossible(ClientEntity clientEntity, List<ProductEntity> products) throws TransactionDeniedException {
        checkIfClientHaveLessThanThreeTransactions(clientEntity, products);
        checkIfTransactionHasMoreThanFiveSameLuxuryProducts(products);
        checkIsWeightBiggerThan25(products);
    }

    private void checkIfTransactionHasMoreThanFiveSameLuxuryProducts(List<ProductEntity> products) throws TransactionDeniedException {
        List<ProductEntity> luxuryProducts = new LinkedList<>();
        for (ProductEntity product : products) {

            if (product.getCost() > 7000D) {
                luxuryProducts.add(product);
            }

            for (int i = 0; i < luxuryProducts.size() - 1; i++) {
                int count = 1;
                for (int j = 1; j < luxuryProducts.size(); j++) {
                    if (luxuryProducts.get(i).getId() == luxuryProducts.get(j).getId()) {
                        count++;
                    }
                    if (count > 5) {
                        throw new TransactionDeniedException();
                    }
                }
            }
        }
    }

    private void checkIfClientHaveLessThanThreeTransactions(ClientEntity clientEntity, List<ProductEntity> products) throws TransactionDeniedException {
        if (clientEntity.getTransactions() == null || clientEntity.getTransactions().size() < 3) {
            Double costSum = 0D;
            for (ProductEntity product : products) {
                costSum += product.getCost();
            }

            if (costSum > 5000) {
                throw new TransactionDeniedException();
            }
        }
    }

    private void addTransactionToClient(ClientEntity clientEntity, TransactionEntity transactionEntity) {
        List<TransactionEntity> transactions;

        if (clientEntity.getTransactions() != null) {
            transactions = clientEntity.getTransactions();
        } else {
            transactions = new LinkedList<>();
        }

        transactions.add(transactionEntity);
        clientEntity.setTransactions(transactions);
        clientRepository.save(clientEntity);
    }

    private void addTransactionToProducts(List<ProductEntity> products, TransactionEntity transactionEntity) {
        List<TransactionEntity> productsTransactions;
        Set<Long> idSet = new TreeSet<>();

        products.forEach(c -> idSet.add(c.getId()));

        List<ProductEntity> distinctProducts = new LinkedList<>();

        idSet.forEach(i -> distinctProducts.add(productRepository.findProductEntityById(i)));

        for (ProductEntity product : distinctProducts) {
            if (product.getTransactions() != null) {
                productsTransactions = product.getTransactions();
            } else {
                productsTransactions = new LinkedList<>();
            }

            productsTransactions.add(transactionEntity);
            product.setTransactions(productsTransactions);
        }

        productRepository.saveAll(distinctProducts);
    }

    @Override
    @Transactional(readOnly = false)
    public void removeTransaction(Long id) {
        TransactionEntity transactionEntity = transactionRepository.findTransactionEntityById(id);

        ClientEntity clientEntity = transactionEntity.getClient();
        removeTransactionFromClient(transactionEntity, clientEntity);

        List<ProductEntity> productEntities = transactionEntity.getProducts();
        removeTransactionFromProducts(transactionEntity, productEntities);

        transactionRepository.deleteById(id);
    }

    private void removeTransactionFromProducts(TransactionEntity transactionEntity, List<ProductEntity> productEntities) {
        for (ProductEntity product : productEntities) {
            List<TransactionEntity> transactionEntities = product.getTransactions();
            transactionEntities.remove(transactionEntity);
            product.setTransactions(transactionEntities);
        }
        productRepository.saveAll(productEntities);
    }


    private void removeTransactionFromClient(TransactionEntity transactionEntity, ClientEntity clientEntity) {
        List<TransactionEntity> clientTransactions = clientEntity.getTransactions();
        clientTransactions.remove(transactionEntity);
        clientEntity.setTransactions(clientTransactions);
        clientRepository.save(clientEntity);
    }

    @Override
    public List<TransactionTO> findAllTransactions() {
        return TransactionMapper.toTransactionTOList(transactionRepository.findAll());
    }

    @Override
    public TransactionTO updateTransaction(TransactionTO transaction) {
        TransactionEntity transactionEntity = transactionRepository.findTransactionEntityById(transaction.getId());
        transactionEntity.setStatus(transaction.getStatus());
        transactionEntity.setQuantity(transaction.getQuantity());
        transactionEntity.setDate(transaction.getDate());

        transactionRepository.save(transactionEntity);
        return TransactionMapper.toTransactionTO(transactionEntity);
    }
}
