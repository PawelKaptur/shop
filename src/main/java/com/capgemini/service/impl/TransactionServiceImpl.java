package com.capgemini.service.impl;


import com.capgemini.entity.TransactionEntity;
import com.capgemini.mapper.TransactionMapper;
import com.capgemini.repository.TransactionRepository;
import com.capgemini.service.TransactionService;
import com.capgemini.type.TransactionTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionTO findTransactionById(Long id) {
        return TransactionMapper.toTransactionTO(transactionRepository.findTransactionEntityById(id));
    }

    @Override
    public TransactionTO addTransaction(TransactionTO transaction) {
        TransactionEntity transactionEntity = transactionRepository.save(TransactionMapper.toTransactionEntity(transaction));
        //tutaj od razu raczej lista produktow i klient, jescze tutaj beda walidacje

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
