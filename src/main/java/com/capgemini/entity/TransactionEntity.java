package com.capgemini.entity;


import com.capgemini.Status;
import com.sun.istack.internal.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "transaction")
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
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private ClientEntity client;

    @ManyToMany
    private List<ProductEntity> products;
}
