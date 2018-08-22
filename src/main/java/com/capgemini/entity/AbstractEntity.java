package com.capgemini.entity;


import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@Data
public abstract class AbstractEntity {

    private Date dateOfCreation;

    private Date dateOfUpdate;

}
