package com.capgemini.repository.custom;

import com.capgemini.entity.ClientEntity;
import com.querydsl.core.Tuple;

import java.util.List;

public interface ClientRepositoryCustom {
    Double costOfAllTransactionsForClient(Long id);
    List<ClientEntity> findClientsByLastName(String lastName);
    List<Tuple> fffff();
}
