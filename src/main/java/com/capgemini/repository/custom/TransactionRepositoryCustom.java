package com.capgemini.repository.custom;

import com.capgemini.Status;

import java.util.Date;

public interface TransactionRepositoryCustom {
    Double calculateProfitBetween(Date startDate, Date endDate);
    Double calculateAllCostOfTransactionsForClient(Long id);
    Double calculateAllCostOfTransactionsWithStatusForClient(Long id, Status status);
    Double calculateAllCostOfTransactionsWithStatusForAllClients(Status status);
}
