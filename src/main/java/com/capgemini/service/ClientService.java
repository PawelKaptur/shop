package com.capgemini.service;

import com.capgemini.entity.ClientEntity;

public interface ClientService {
    ClientEntity findClientById(Long id);

    ClientEntity addClient(ClientEntity clientEntity);
}
