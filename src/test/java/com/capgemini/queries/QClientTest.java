package com.capgemini.queries;

import com.capgemini.Status;
import com.capgemini.exception.TransactionDeniedException;
import com.capgemini.repository.ClientRepository;
import com.capgemini.service.ClientService;
import com.capgemini.service.ProductService;
import com.capgemini.service.TransactionService;
import com.capgemini.type.ClientTO;
import com.capgemini.type.ProductTO;
import com.capgemini.type.TransactionTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class QClientTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ProductService productService;

    @Test
    @Transactional
    public void shouldFindTwoClients() {
        //given
        String lastName = "Malysz";
        ClientTO client = new ClientTO();
        client.setFirstName("Adam");
        client.setLastName(lastName);
        client.setAddress("asdsadsa 123sa qwe");
        client.setDateOfBirth(new Date());
        client.setEmail("adam.malysz@gmail.com");
        client.setTelephone(2312312321L);
        clientService.addClient(client);
        clientService.addClient(client);
        client.setLastName("Dluzysz");
        clientService.addClient(client);

        //when
        List<ClientTO> clients = clientService.findClientsByLastName(lastName);

        //then
        assertThat(clients.size()).isEqualTo(2);
    }


}
