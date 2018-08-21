package com.capgemini.entity;


import com.sun.istack.internal.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Double cost;

    @NotNull
    private Double margin;

    @NotNull
    private Double weight;

    @ManyToMany
    private List<TransactionEntity> transactions;
}
