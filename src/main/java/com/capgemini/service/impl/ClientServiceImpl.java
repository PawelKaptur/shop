package com.capgemini.service.impl;


import com.capgemini.entity.ClientEntity;
import com.capgemini.entity.ProductEntity;
import com.capgemini.entity.TransactionEntity;
import com.capgemini.mapper.ClientMapper;
import com.capgemini.repository.ClientRepository;
import com.capgemini.repository.ProductRepository;
import com.capgemini.service.ClientService;
import com.capgemini.type.ClientTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private ProductRepository productRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, ProductRepository productRepository) {
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
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
        ClientEntity clientEntity = clientRepository.findClientEntityById(id);
        List<TransactionEntity> transactionEntities = clientEntity.getTransactions();
        removeTransactionFromProducts(transactionEntities);

        clientRepository.deleteById(id);
    }

    private void removeTransactionFromProducts(List<TransactionEntity> transactionEntities) {
        for (TransactionEntity transaction : transactionEntities) {
            List<ProductEntity> products = transaction.getProducts();
            for (ProductEntity product : products) {
                List<TransactionEntity> transactions = product.getTransactions();
                transactions.remove(transaction);
                product.setTransactions(transactions);
            }
            productRepository.saveAll(products);
        }
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
        return ClientMapper.toClientTO(clientEntity);
    }

    @Override
    public List<ClientTO> findThreeBestClientsBetween(Date startDate, Date endDate) {
        return ClientMapper.toClientTOList(clientRepository.findThreeBestClientsBetween(startDate, endDate));
    }

    @Override
    public List<ClientTO> findClientsByLastName(String lastName) {
        return ClientMapper.toClientTOList(clientRepository.findClientsByLastName(lastName));
    }
}
