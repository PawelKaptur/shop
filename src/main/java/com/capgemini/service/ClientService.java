package com.capgemini.service;

import com.capgemini.type.ClientTO;

public interface ClientService {
    ClientTO findClientById(Long id);

    ClientTO addClient(ClientTO client);
}
