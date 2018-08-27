package com.capgemini.service;

import com.capgemini.Status;
import com.capgemini.entity.ClientEntity;
import com.capgemini.entity.TransactionEntity;
import com.capgemini.exception.TransactionDeniedException;
import com.capgemini.repository.ClientRepository;
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
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class ClientTest {

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ProductService productService;

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

    @Test
    @Transactional
    public void shouldUpdateClientWithTransaction() throws TransactionDeniedException {
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

        ProductTO product = new ProductTO();
        product.setWeight(2D);
        product.setMargin(0.2);
        product.setCost(1000D);
        product.setName("qwertz");

        ProductTO addedProduct = productService.addProduct(product);

        List<Long> products = new LinkedList<>();
        products.add(addedProduct.getId());

        TransactionTO transaction = new TransactionTO();
        transaction.setDate(new Date());
        transaction.setStatus(Status.WAITING_FOR_PAYMENT);
        transaction.setProducts(products);
        transaction.setClient(addedClient.getId());
        transaction.setQuantity(products.size());

        //when
        TransactionTO addedTransaction = transactionService.addTransaction(transaction);
        addedClient.setLastName(lastName);
        ClientTO updatedClient = clientService.updateClient(addedClient);


        //then
        assertThat(updatedClient.getId()).isEqualTo(addedClient.getId());
        assertThat(clientService.findClientById(updatedClient.getId()).getLastName()).isEqualTo(lastName);
        assertThat(updatedClient.getTransactions()).isNotNull();
        assertThat(clientService.findClientById(updatedClient.getId()).getTransactions().get(0)).isEqualTo(addedTransaction.getId());
    }

    @Test
    @Transactional
    public void shouldRemoveClientWithTransactions() throws TransactionDeniedException {
        //given
        ClientTO client = new ClientTO();
        client.setFirstName("Adam");
        client.setLastName("Malysz");
        client.setAddress("asdsadsa 123sa qwe");
        client.setDateOfBirth(new Date());
        client.setEmail("adam.malysz@gmail.com");
        client.setTelephone(2312312321L);
        ClientTO addedClient = clientService.addClient(client);

        ProductTO product = new ProductTO();
        product.setWeight(2D);
        product.setMargin(0.2);
        product.setCost(1000D);
        product.setName("qwertz");

        ProductTO addedProduct = productService.addProduct(product);

        List<Long> products = new LinkedList<>();
        products.add(addedProduct.getId());

        TransactionTO transaction = new TransactionTO();
        transaction.setDate(new Date());
        transaction.setStatus(Status.WAITING_FOR_PAYMENT);
        transaction.setProducts(products);
        transaction.setClient(addedClient.getId());
        transaction.setQuantity(products.size());

        //when
        TransactionTO addedTransaction = transactionService.addTransaction(transaction);
        clientService.removeClient(addedClient.getId());
        
        //then
        assertThat(clientService.findClientById(addedClient.getId())).isNull();
        assertThat(productService.findProductById(addedProduct.getId())).isNotNull();
        assertThat(productService.findProductById(addedProduct.getId()).getTransactions()).isEmpty();
        assertThat(transactionService.findTransactionById(addedTransaction.getId())).isNull();
    }
}
