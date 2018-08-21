package com.capgemini.service.impl;


import com.capgemini.entity.ClientEntity;
import com.capgemini.mapper.ClientMapper;
import com.capgemini.repository.ClientRepository;
import com.capgemini.service.ClientService;
import com.capgemini.type.ClientTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientTO findClientById(Long id) {
        return ClientMapper.toClientTO(clientRepository.findClientEntityById(id));
    }

    @Override
    @Transactional(readOnly = false)
    public ClientTO addClient(ClientTO clientTO) {
        ClientEntity savedClient = clientRepository.save(ClientMapper.toClientEntity(clientTO));
        return ClientMapper.toClientTO(savedClient);
    }
}
