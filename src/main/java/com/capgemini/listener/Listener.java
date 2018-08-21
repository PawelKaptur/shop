package com.capgemini.listener;

import com.capgemini.entity.ClientEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

public class Listener {

    @PrePersist
    public void generateDateOfCreation(ClientEntity client){
        client.setDateOfCreation(new Date());
    }

    @PreUpdate
    public void generateDateOfUpdate(ClientEntity client){
        client.setDateOfUpdate(new Date());
    }
}
