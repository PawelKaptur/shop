package com.capgemini.service;

import com.capgemini.type.ClientTO;

import java.util.List;

public interface ClientService {

    ClientTO findClientById(Long id);

    ClientTO addClient(ClientTO client);

    void removeClient(Long id);

    List<ClientTO> findAllClients();

    ClientTO updateClient(ClientTO client);

    List<ClientTO> findClientsByLastName(String lastName);
}
