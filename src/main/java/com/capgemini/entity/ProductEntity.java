package com.capgemini.entity;

import com.capgemini.listener.Listener;
import lombok.Data;
import org.springframework.data.annotation.Version;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "product")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners(Listener.class)
public class ProductEntity extends AbstractEntity {

    @Column(nullable = false)
    private String name;

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
}
