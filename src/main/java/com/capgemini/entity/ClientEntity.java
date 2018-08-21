package com.capgemini.entity;

import com.sun.istack.internal.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private Long telephone;

    @NotNull
    private String address;

    @NotNull
    private Date dateOfBirth;

    @OneToMany
    private List<TransactionEntity> transactions;
}
