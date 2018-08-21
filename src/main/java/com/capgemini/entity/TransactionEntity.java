package com.capgemini.entity;


import com.sun.istack.internal.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class TransactionEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Date date;

    @NotNull
    @Enumerated
    private Status status;

    @NotNull
    private Integer quantity;

    @ManyToOne
    private ClientEntity client;

    @ManyToMany
    private List<ProductEntity> products;
}
