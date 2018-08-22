package com.capgemini.type;

import com.capgemini.Status;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TransactionTO {
    private Long id;
    private Date date;
    private Status status;
    private Integer quantity;
    private Long client;
    private List<Long> products;
}
