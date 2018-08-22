package com.capgemini.service.impl;


import com.capgemini.entity.ClientEntity;
import com.capgemini.entity.ProductEntity;
import com.capgemini.entity.TransactionEntity;
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

    //jeszcze validator
    @Override
    @Transactional(readOnly = false)
    public TransactionTO addTransaction(TransactionTO transaction) {
        TransactionEntity transactionEntity = TransactionMapper.toTransactionEntity(transaction);
        ClientEntity clientEntity = clientRepository.findClientEntityById(transaction.getClient());
        transactionEntity.setClient(clientEntity);

        List<Long> productsId = transaction.getProducts();
        List<ProductEntity> products = new LinkedList<>();

        for (Long id : productsId) {
            products.add(productRepository.findProductEntityById(id));
        }

        transactionEntity.setProducts(products);
        transactionRepository.save(transactionEntity);

        addTransactionToClient(clientEntity, transactionEntity);
        addTransactionToProducts(products, transactionEntity);
        return TransactionMapper.toTransactionTO(transactionEntity);
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

        for (ProductEntity product : products) {
            if (product.getTransactions() != null) {
                productsTransactions = product.getTransactions();
            } else {
                productsTransactions = new LinkedList<>();
            }

            productsTransactions.add(transactionEntity);
            product.setTransactions(productsTransactions);
        }

        productRepository.saveAll(products);
    }

    @Override
    public void removeTransaction(Long id) {
        transactionRepository.deleteById(id);
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
