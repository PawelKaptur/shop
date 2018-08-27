package com.capgemini.repository.custom;

import com.capgemini.entity.ClientEntity;

import java.util.Date;
import java.util.List;

public interface ClientRepositoryCustom {
    List<ClientEntity> findClientsByLastName(String lastName);

    List<ClientEntity> findThreeBestClientsBetween(Date startDate, Date endDate);
}
