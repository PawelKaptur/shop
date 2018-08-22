package com.capgemini.service;

import com.capgemini.entity.ClientEntity;
import com.capgemini.repository.ClientRepository;
import com.capgemini.type.ClientTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class ClientTest {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    @Transactional
    public void shouldAddClient(){
        //given
        ClientTO client = new ClientTO();
        client.setFirstName("Adam");
        client.setLastName("Malysz");
        client.setAddress("asdsadsa 123sa qwe");
        client.setDateOfBirth(new Date());
        client.setEmail("adam.malysz@gmail.com");
        client.setTelephone(2312312321L);
        ClientTO addedClient = clientService.addClient(client);

        //when
        ClientTO selectedClient = clientService.findClientById(addedClient.getId());

        //then
        assertThat(selectedClient.getId()).isEqualTo(addedClient.getId());
    }

    @Test
    @Transactional
    public void shouldRemoveClient(){
        //given
        ClientTO client = new ClientTO();
        client.setFirstName("Adam");
        client.setLastName("Malysz");
        client.setAddress("asdsadsa 123sa qwe");
        client.setDateOfBirth(new Date());
        client.setEmail("adam.malysz@gmail.com");
        client.setTelephone(2312312321L);
        ClientTO addedClient = clientService.addClient(client);

        //when
        clientService.removeClient(addedClient.getId());

        //then
        assertThat(clientService.findClientById(addedClient.getId())).isNull();
    }

    @Test
    @Transactional
    public void shouldAddThreeClients(){
        //given
        ClientTO client = new ClientTO();
        client.setFirstName("Adam");
        client.setLastName("Malysz");
        client.setAddress("asdsadsa 123sa qwe");
        client.setDateOfBirth(new Date());
        client.setEmail("adam.malysz@gmail.com");
        client.setTelephone(2312312321L);
        clientService.addClient(client);
        clientService.addClient(client);
        clientService.addClient(client);

        //when
        List<ClientTO> clients = clientService.findAllClients();

        //then
        assertThat(clients.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    public void shouldHaveCreationDate(){
        //given
        ClientTO client = new ClientTO();
        client.setFirstName("Adam");
        client.setLastName("Malysz");
        client.setAddress("asdsadsa 123sa qwe");
        client.setDateOfBirth(new Date());
        client.setEmail("adam.malysz@gmail.com");
        client.setTelephone(2312312321L);
        ClientTO addedClient = clientService.addClient(client);

        //when
        ClientEntity selectedClient = clientRepository.findClientEntityById(addedClient.getId());

        //then
        assertThat(selectedClient.getDateOfCreation()).isNotNull();
        assertThat(selectedClient.getDateOfUpdate()).isNull();
    }

    @Test
    @Transactional
    public void shouldUpdateLastName(){
        //given
        ClientTO client = new ClientTO();
        client.setFirstName("Adam");
        client.setLastName("Malysz");
        client.setAddress("asdsadsa 123sa qwe");
        client.setDateOfBirth(new Date());
        client.setEmail("adam.malysz@gmail.com");
        client.setTelephone(2312312321L);
        ClientTO addedClient = clientService.addClient(client);

        String lastName = "Dluzysz";

        //when
        addedClient.setLastName(lastName);
        ClientTO updatedClient = clientService.updateClient(addedClient);
        List<ClientTO> clients = clientService.findAllClients();

        //then
        assertThat(updatedClient.getLastName().equals(lastName));
        assertThat(updatedClient.getId()).isEqualTo(addedClient.getId());
        assertThat(clientService.findClientById(updatedClient.getId()).getLastName()).isEqualTo(lastName);
        assertThat(clients.size()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void shouldUpdateDate() throws InterruptedException {
        //given
        ClientTO client = new ClientTO();
        client.setFirstName("Adam");
        client.setLastName("Malysz");
        client.setAddress("asdsadsa 123sa qwe");
        client.setDateOfBirth(new Date());
        client.setEmail("adam.malysz@gmail.com");
        client.setTelephone(2312312321L);
        ClientTO addedClient = clientService.addClient(client);

        String lastName = "Duzysz";
        //when
        addedClient.setLastName(lastName);
        Date createDate1 = clientRepository.findClientEntityById(addedClient.getId()).getDateOfCreation();
        TimeUnit.SECONDS.sleep(1);
        clientService.updateClient(addedClient);
        ClientEntity selectedClient = clientRepository.findClientEntityById(addedClient.getId());
        Date createDate2 = clientRepository.findClientEntityById(selectedClient.getId()).getDateOfCreation();

        //then
        assertThat(selectedClient.getDateOfCreation()).isNotNull();
        assertThat(createDate1).isEqualTo(createDate2);
        assertThat(selectedClient.getDateOfUpdate()).isNotNull();
    }

    @Test
    @Transactional
    public void versionTest(){
        //given
        ClientTO client = new ClientTO();
        client.setFirstName("Adam");
        client.setLastName("Malysz");
        client.setAddress("asdsadsa 123sa qwe");
        client.setDateOfBirth(new Date());
        client.setEmail("adam.malysz@gmail.com");
        client.setTelephone(2312312321L);
        ClientTO addedClient = clientService.addClient(client);

        String lastName = "Duzysz";

        //when
        addedClient.setLastName(lastName);
        Integer version1 = clientRepository.findClientEntityById(addedClient.getId()).getVersion();
        clientService.updateClient(addedClient);
        Integer version2 = clientRepository.findClientEntityById(addedClient.getId()).getVersion();

        //then
        assertThat(version1).isNotEqualTo(version2);
    }
}
