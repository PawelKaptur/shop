package com.capgemini.repository.custom;

import com.capgemini.entity.TransactionEntity;

import java.util.Date;
import java.util.List;

public interface TransactionRepositoryCustom {
    List<TransactionEntity> profitBetween(Date startDate, Date endDate);
}
