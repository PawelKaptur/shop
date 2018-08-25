package com.capgemini.repository.custom;

import com.capgemini.entity.ClientEntity;

import java.util.List;

public interface ClientRepositoryCustom {
    List<ClientEntity> findClientsByLastName(String lastName);
}
