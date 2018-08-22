package com.capgemini.mapper;

import com.capgemini.entity.ClientEntity;
import com.capgemini.type.ClientTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientMapper {

    public static ClientTO toClientTO(ClientEntity clientEntity){
        if(clientEntity == null){
            return null;
        }

        ClientTO clientTO = new ClientTO();
        clientTO.setAddress(clientEntity.getAddress());
        clientTO.setDateOfBirth(clientEntity.getDateOfBirth());
        clientTO.setEmail(clientEntity.getEmail());
        clientTO.setFirstName(clientEntity.getFirstName());
        clientTO.setId(clientEntity.getId());
        clientTO.setLastName(clientEntity.getLastName());
        clientTO.setTelephone(clientEntity.getTelephone());

        if(clientEntity.getTransactions() != null){
            clientTO.setTransactions(clientEntity.getTransactions().stream().map(t -> t.getId()).collect(Collectors.toList()));
        }

        return clientTO;
    }

    public static ClientEntity toClientEntity(ClientTO clientTO){
        if(clientTO == null){
            return null;
        }

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setFirstName(clientTO.getFirstName());
        clientEntity.setLastName(clientTO.getLastName());
        clientEntity.setTelephone(clientTO.getTelephone());
        clientEntity.setEmail(clientTO.getEmail());
        clientEntity.setDateOfBirth(clientTO.getDateOfBirth());
        clientEntity.setAddress(clientTO.getAddress());

        return clientEntity;
    }

    public static List<ClientTO> toClientTOList(Iterable<ClientEntity> all) {
        Iterator<ClientEntity> it = all.iterator();
        List<ClientTO> clientsTO = new LinkedList<>();
        while(it.hasNext()){
            clientsTO.add(toClientTO(it.next()));
        }

        return clientsTO;
    }
}
