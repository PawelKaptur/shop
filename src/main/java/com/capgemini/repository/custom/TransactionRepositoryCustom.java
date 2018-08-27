package com.capgemini.repository.custom;

import com.capgemini.Status;
import com.capgemini.TransactionSearchCriteria;
import com.capgemini.entity.TransactionEntity;

import java.util.Date;
import java.util.List;

public interface TransactionRepositoryCustom {
    Double calculateProfitBetween(Date startDate, Date endDate);
    Double calculateAllCostOfTransactionsForClient(Long id);
    Double calculateAllCostOfTransactionsWithStatusForClient(Long id, Status status);
    Double calculateAllCostOfTransactionsWithStatusForAllClients(Status status);
    List<TransactionEntity> searchTransactionByCriteria(TransactionSearchCriteria transactionSearchCriteria);
}
