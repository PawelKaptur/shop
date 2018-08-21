package com.capgemini.service.impl;


import com.capgemini.entity.ClientEntity;
import com.capgemini.repository.ClientRepository;
import com.capgemini.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientEntity findClientById(Long id) {
        return clientRepository.findClientEntityById(id);
    }

    @Override
    public ClientEntity addClient(ClientEntity clientEntity) {
        ClientEntity savedClient = clientRepository.save(clientEntity);
        return savedClient;
    }
}
