package com.capgemini.entity;

import com.capgemini.listener.Listener;
import lombok.Data;
import org.springframework.data.annotation.Version;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "client")
@EntityListeners(Listener.class)
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Long telephone;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Date dateOfBirth;

    @OneToMany
    private List<TransactionEntity> transactions;

    @Version
    private int version;

    private Date dateOfCreation;

    private Date dateOfUpdate;
}
