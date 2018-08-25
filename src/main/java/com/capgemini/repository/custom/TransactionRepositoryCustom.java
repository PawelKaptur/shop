package com.capgemini.repository.custom;

import java.util.Date;

public interface TransactionRepositoryCustom {
    Double calculateProfitBetween(Date startDate, Date endDate);
}
