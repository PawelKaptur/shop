package com.capgemini.entity;

import lombok.Data;
import org.springframework.data.annotation.Version;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Double cost;

    @Column(nullable = false)
    private Double margin;

    @Column(nullable = false)
    private Double weight;

    @ManyToMany
    private List<TransactionEntity> transactions;

    @Version
    private int version;

    private Date dateOfCreation;

    private Date dateOfUpdate;
}
