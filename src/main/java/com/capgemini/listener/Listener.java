package com.capgemini.listener;

import com.capgemini.entity.AbstractEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

public class Listener {

    @PrePersist
    public void generateDateOfCreation(AbstractEntity entity) {
        entity.setDateOfCreation(new Date());
    }

    @PreUpdate
    public void generateDateOfUpdate(AbstractEntity entity) {
        entity.setDateOfUpdate(new Date());
    }
}
