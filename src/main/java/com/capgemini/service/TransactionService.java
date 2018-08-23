package com.capgemini.service;

import com.capgemini.exception.TransactionDeniedException;
import com.capgemini.type.TransactionTO;

import java.util.List;

public interface TransactionService {
    TransactionTO findTransactionById(Long id);

    TransactionTO addTransaction(TransactionTO transaction) throws TransactionDeniedException;

    void removeTransaction(Long id);

    List<TransactionTO> findAllTransactions();

    TransactionTO updateTransaction(TransactionTO transaction);
}
