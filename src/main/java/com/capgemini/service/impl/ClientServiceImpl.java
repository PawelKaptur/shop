package com.capgemini.service.impl;


import com.capgemini.entity.ClientEntity;
import com.capgemini.mapper.ClientMapper;
import com.capgemini.repository.ClientRepository;
import com.capgemini.service.ClientService;
import com.capgemini.type.ClientTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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

    @Override
    @Transactional(readOnly = false)
    public void removeClient(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public List<ClientTO> findAllClients() {
        return ClientMapper.toClientTOList(clientRepository.findAll());
    }

    @Override
    @Transactional(readOnly = false)
    public ClientTO updateClient(ClientTO clientTO) {
        ClientEntity clientEntity = clientRepository.findClientEntityById(clientTO.getId());
        clientEntity.setLastName(clientTO.getLastName());
        clientEntity.setAddress(clientTO.getAddress());
        clientEntity.setDateOfBirth(clientTO.getDateOfBirth());
        clientEntity.setEmail(clientTO.getEmail());
        clientEntity.setTelephone(clientTO.getTelephone());
        clientEntity.setFirstName(clientTO.getFirstName());
        clientRepository.save(clientEntity);
        //ClientEntity clientEntity = clientRepository.save(ClientMapper.toClientEntity(client));
        return ClientMapper.toClientTO(clientEntity);
    }
}
