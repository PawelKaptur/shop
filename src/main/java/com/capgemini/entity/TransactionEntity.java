package com.capgemini.entity;


import com.capgemini.Status;
import com.capgemini.listener.Listener;
import lombok.Data;
import org.springframework.data.annotation.Version;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "transaction")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners(Listener.class)
public class TransactionEntity extends AbstractEntity {

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    @Enumerated
    private Status status;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private ClientEntity client;

    @ManyToMany
    private List<ProductEntity> products;

    @Version
    private int version;
}
