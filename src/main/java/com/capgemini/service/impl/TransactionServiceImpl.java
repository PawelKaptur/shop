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

    @Override
    @Transactional(readOnly = false)
    public TransactionTO addTransaction(TransactionTO transaction) {
        TransactionEntity transactionEntity = TransactionMapper.toTransactionEntity(transaction);
        ClientEntity clientEntity = clientRepository.findClientEntityById(transaction.getClient());
        transactionEntity.setClient(clientEntity);

        List<Long> productsId = transaction.getProducts();
        List<ProductEntity> products = new LinkedList<>();

        for(Long id: productsId){
            products.add(productRepository.findProductEntityById(id));
        }

        transactionEntity.setProducts(products);
        transactionRepository.save(transactionEntity);


        List<TransactionEntity> transactions;

        if(clientEntity.getTransactions() != null) {
            transactions = clientEntity.getTransactions();
        }
        else{
            transactions = new LinkedList<>();
        }

        transactions.add(transactionEntity);
        clientEntity.setTransactions(transactions);
        clientRepository.save(clientEntity);

        //jeszcze bedzie trzeba uaktualnic w produkcie i kliencie, i jeszcze validator
        return TransactionMapper.toTransactionTO(transactionEntity);
    }

    @Override
    public void removeTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public List<TransactionTO> findAllTransactions() {
        return null;
    }

    @Override
    public TransactionTO updateTransaction(TransactionTO transaction) {
        return null;
    }
}
