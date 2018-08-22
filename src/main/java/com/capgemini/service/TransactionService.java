package com.capgemini.service;

import com.capgemini.type.TransactionTO;

import java.util.List;

public interface TransactionService {
    TransactionTO findTransactionById(Long id);

    TransactionTO addTransaction(TransactionTO transaction);

    void removeTransaction(Long id);

    List<TransactionTO> findAllTransactions();

    TransactionTO updateTransaction(TransactionTO transaction);
}
