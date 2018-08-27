package com.capgemini;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionSearchCriteria {

    private String lastName;
    private Date startDate;
    private Date endDate;
    private Long productId;
    private Double costOfTransaction;
}
