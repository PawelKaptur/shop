package com.capgemini.service;

import com.capgemini.Status;
import com.capgemini.TransactionSearchCriteria;
import com.capgemini.exception.TransactionDeniedException;
import com.capgemini.type.TransactionTO;

import java.util.Date;
import java.util.List;

public interface TransactionService {
    TransactionTO findTransactionById(Long id);

    TransactionTO addTransaction(TransactionTO transaction) throws TransactionDeniedException;

    void removeTransaction(Long id);

    List<TransactionTO> findAllTransactions();

    TransactionTO updateTransaction(TransactionTO transaction);

    Double calculateProfitBetween(Date startDate, Date endDate);

    Double calculateAllCostOfTransactionsForClient(Long id);

    Double calculateAllCostOfTransactionsWithStatusForClient(Long id, Status status);

    Double calculateAllCostOfTransactionsWithStatusForAllClients(Status status);

    List<TransactionTO> searchTransactionByCriteria(TransactionSearchCriteria transactionSearchCriteria);
}
