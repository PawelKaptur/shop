package com.capgemini.service;


import com.capgemini.entity.ClientEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class ClientTest {

    @Autowired
    private ClientService clientService;

    @Test
    public void shouldAddClient(){
        //given
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setFirstName("Adam");
        clientEntity.setLastName("Malysz");
        clientEntity.setAddress("asdsadsa 123sa qwe");
        clientEntity.setDateOfBirth(new Date());
        clientEntity.setEmail("adam.malysz@gmail.com");
        clientEntity.setTelephone(2312312321L);
        ClientEntity savedClient = clientService.addClient(clientEntity);

        //when
        ClientEntity selectedClient = clientService.findClientById(savedClient.getId());
        
        //then
        assertThat(selectedClient.getId()).isEqualTo(savedClient.getId());
    }
}
