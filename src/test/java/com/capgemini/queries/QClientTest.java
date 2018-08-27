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

    @Test
    @Transactional
    public void shouldShowThreeBestClientsBetweenTwoDates() throws TransactionDeniedException {
        //given
        ClientTO client = new ClientTO();
        client.setFirstName("Adam");
        client.setLastName("Malysz");
        client.setAddress("asdsadsa 123sa qwe");
        client.setDateOfBirth(new Date());
        client.setEmail("adam.malysz@gmail.com");
        client.setTelephone(2312312321L);
        ClientTO addedClient = clientService.addClient(client);
        ClientTO addedClient2 = clientService.addClient(client);
        ClientTO addedClient3 = clientService.addClient(client);
        ClientTO addedClient4 = clientService.addClient(client);

        ProductTO product = new ProductTO();
        product.setWeight(2D);
        product.setMargin(0.2);
        product.setCost(1000D);
        product.setName("qwertz");

        ProductTO addedProduct = productService.addProduct(product);

        product.setCost(500D);
        product.setMargin(0.1D);
        ProductTO addedProduct2 = productService.addProduct(product);

        List<Long> products = new LinkedList<>();
        products.add(addedProduct.getId());
        products.add(addedProduct2.getId());

        TransactionTO transaction = new TransactionTO();
        transaction.setDate(new Date(1000L));
        transaction.setStatus(Status.IN_REALIZATION);
        transaction.setProducts(products);
        transaction.setClient(addedClient.getId());
        transaction.setQuantity(products.size());

        transactionService.addTransaction(transaction);
        transactionService.addTransaction(transaction);
        transaction.setDate(new Date(2000L));
        transaction.setClient(addedClient2.getId());
        transactionService.addTransaction(transaction);

        transaction.setClient(addedClient4.getId());
        transaction.setDate(new Date(3000L));
        transactionService.addTransaction(transaction);
        transactionService.addTransaction(transaction);
        transactionService.addTransaction(transaction);

        transaction.setClient(addedClient3.getId());
        transaction.setDate(new Date(4000L));
        transactionService.addTransaction(transaction);
        transactionService.addTransaction(transaction);
        transactionService.addTransaction(transaction);
        transactionService.addTransaction(transaction);

        transaction.setDate(new Date(1500L));
        products.clear();
        products.add(addedProduct2.getId());
        transaction.setProducts(products);
        transactionService.addTransaction(transaction);

        //when
        List<ClientTO> clients = clientService.findThreeBestClientsBetween(new Date(500L), new Date(5500L));
        List<ClientTO> clients2 = clientService.findThreeBestClientsBetween(new Date(2500L), new Date(4500L));
        List<ClientTO> clients3 = clientService.findThreeBestClientsBetween(new Date(500L), new Date(3500L));
        List<ClientTO> clients0 = clientService.findThreeBestClientsBetween(new Date(500L), new Date(700L));

        assertThat(clients.size()).isEqualTo(3);
        assertThat(clients.get(0).getId()).isEqualTo(addedClient3.getId());
        assertThat(clients2.size()).isEqualTo(2);
        assertThat(clients.get(0).getId()).isEqualTo(addedClient3.getId());
        assertThat(clients3.size()).isEqualTo(3);
        assertThat(clients3.get(0).getId()).isEqualTo(addedClient4.getId());
        assertThat(clients0.size()).isEqualTo(0);
    }
}
