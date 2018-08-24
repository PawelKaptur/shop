package com.capgemini.queries;

import com.capgemini.Status;
import com.capgemini.exception.TransactionDeniedException;
import com.capgemini.repository.ProductRepository;
import com.capgemini.service.ClientService;
import com.capgemini.service.ProductService;
import com.capgemini.service.TransactionService;
import com.capgemini.type.ClientTO;
import com.capgemini.type.ProductTO;
import com.capgemini.type.TransactionTO;
import com.querydsl.core.Tuple;
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
public class QProductTest {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProductService productService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @Transactional
    public void shouldFindTwoItemsWithQuantityOfThree() throws TransactionDeniedException {
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

        ProductTO product = new ProductTO();
        product.setWeight(2D);
        product.setMargin(0.2);
        product.setCost(1000D);
        product.setName("qwertz");

        ProductTO addedProduct = productService.addProduct(product);

        product.setName("asdfgh");
        ProductTO addedProduct2 = productService.addProduct(product);

        List<Long> products = new LinkedList<>();
        products.add(addedProduct.getId());
        products.add(addedProduct2.getId());
        products.add(addedProduct2.getId());

        TransactionTO transaction = new TransactionTO();
        transaction.setDate(new Date());
        transaction.setStatus(Status.IN_REALIZATION);
        transaction.setProducts(products);
        transaction.setClient(addedClient.getId());
        transaction.setQuantity(products.size());

        transactionService.addTransaction(transaction);
        transactionService.addTransaction(transaction);
        transactionService.addTransaction(transaction);

        transaction.setClient(addedClient2.getId());
        transaction.setStatus(Status.CANCELED);
        transactionService.addTransaction(transaction);

        //when
        List<Tuple> items = productRepository.findItemsInTransactionInRealization();

        //then
        System.out.println(items.get(0).toArray()[0]);
        assertThat(items.size()).isEqualTo(2);
        assertThat(items.get(0).size()).isEqualTo(2);
        assertThat(items.get(0).toArray()[0]).isEqualTo(addedProduct.getName());
        assertThat(items.get(0).toArray()[1]).isEqualTo(3L);
        assertThat(items.get(1).toArray()[0]).isEqualTo(addedProduct2.getName());
        assertThat(items.get(1).toArray()[1]).isEqualTo(6L);
    }

    @Test
    @Transactional
    public void shouldFindTenBestSellers() throws TransactionDeniedException {
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

        ProductTO product = new ProductTO();
        product.setWeight(2D);
        product.setMargin(0.2);
        product.setCost(1001D);
        product.setName("qwertz");

        ProductTO addedProduct = productService.addProduct(product);

        product.setName("asdfgh");
        ProductTO addedProduct2 = productService.addProduct(product);

        List<Long> products = new LinkedList<>();
        products.add(addedProduct.getId());
        products.add(addedProduct2.getId());
        products.add(addedProduct2.getId());

        TransactionTO transaction = new TransactionTO();
        transaction.setDate(new Date());
        transaction.setStatus(Status.IN_REALIZATION);
        transaction.setProducts(products);
        transaction.setClient(addedClient.getId());
        transaction.setQuantity(products.size());

        transactionService.addTransaction(transaction);
    }
}
