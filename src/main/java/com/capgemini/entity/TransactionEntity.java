package com.capgemini.entity;


import com.capgemini.Status;
import lombok.Data;
import org.springframework.data.annotation.Version;


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

    private Date dateOfCreation;

    private Date dateOfUpdate;
}
