package com.capgemini.type;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ClientTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long telephone;
    private String address;
    private Date dateOfBirth;
    private List<Long> transactions;
}
