package com.capgemini.service;


import com.capgemini.Status;
import com.capgemini.exception.TransactionDeniedException;
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
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class TransactionTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProductService productService;

    @Test
    @Transactional
    public void shouldAddTransaction() throws TransactionDeniedException {
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
        ProductTO addedProduct2 = productService.addProduct(product);

        List<Long> products = new LinkedList<>();
        products.add(addedProduct.getId());
        products.add(addedProduct2.getId());

        TransactionTO transaction = new TransactionTO();
        transaction.setDate(new Date());
        transaction.setStatus(Status.WAITING_FOR_PAYMENT);
        transaction.setProducts(products);
        transaction.setClient(addedClient.getId());
        transaction.setQuantity(products.size());

        TransactionTO addedTransaction = transactionService.addTransaction(transaction);

        //when
        TransactionTO selectedTransaction = transactionService.findTransactionById(addedTransaction.getId());

        //then
        assertThat(selectedTransaction.getId()).isEqualTo(selectedTransaction.getId());
        assertThat(selectedTransaction.getProducts().size()).isEqualTo(2);
        assertThat(clientService.findClientById(addedClient.getId()).getTransactions().size()).isEqualTo(1);
        assertThat(clientService.findClientById(addedClient.getId()).getTransactions().get(0)).isEqualTo(addedTransaction.getId());
        assertThat(productService.findProductById(addedProduct.getId()).getTransactions().size()).isEqualTo(1);
        //assertThat(productService.findProductById(addedProduct.getId()).getTransactions().get(0)).isEqualTo(addedTransaction.getId());
        assertThat(productService.findProductById(addedProduct2.getId()).getTransactions().size()).isEqualTo(1);
        //assertThat(productService.findProductById(addedProduct2.getId()).getTransactions().get(0)).isEqualTo(addedTransaction.getId());
    }

    @Test
    @Transactional
    public void shouldRemoveTransaction() throws TransactionDeniedException {
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

        TransactionTO addedTransaction = transactionService.addTransaction(transaction);
        TransactionTO addedTransaction2 = transactionService.addTransaction(transaction);

        //when
        transactionService.removeTransaction(addedTransaction.getId());
        List<TransactionTO> transactions = transactionService.findAllTransactions();

        //then
        assertThat(transactions.size()).isEqualTo(1);
        assertThat(productService.findProductById(addedProduct.getId()).getTransactions().contains(addedTransaction.getId())).isFalse();
        assertThat(productService.findProductById(addedProduct.getId()).getTransactions().contains(addedTransaction2.getId())).isTrue();
        assertThat(clientService.findClientById(addedClient.getId()).getTransactions().contains(addedTransaction.getId())).isFalse();
        assertThat(clientService.findClientById(addedClient.getId()).getTransactions().contains(addedTransaction2.getId())).isTrue();
    }

    @Test
    @Transactional
    public void shouldFindThreeTransactions() throws TransactionDeniedException {
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

        transactionService.addTransaction(transaction);
        transactionService.addTransaction(transaction);
        transactionService.addTransaction(transaction);

        //when
        List<TransactionTO> transactions = transactionService.findAllTransactions();

        //then
        assertThat(transactions.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    public void shouldUpdateTransaction() throws TransactionDeniedException {
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

        TransactionTO addedTransaction = transactionService.addTransaction(transaction);

        //when
        Status status = Status.CANCELED;
        addedTransaction.setStatus(status);
        TransactionTO updatedTransaction = transactionService.updateTransaction(addedTransaction);

        //then
        assertThat(updatedTransaction.getId()).isEqualTo(addedTransaction.getId());
        assertThat(transactionService.findTransactionById(updatedTransaction.getId()).getStatus()).isEqualTo(status);
    }

    @Test
    @Transactional
    public void shouldThrowExceptionBecauseToBigCost() throws TransactionDeniedException {
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
        product.setCost(5001D);
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
        boolean exceptionThrown = false;
        try {
            transactionService.addTransaction(transaction);
        } catch (TransactionDeniedException e){
            exceptionThrown = true;
        }

        //then
        assertTrue(exceptionThrown);
    }

    @Test
    @Transactional
    public void shouldAddTransactionBecauseMoreThanTwoTransactions() throws TransactionDeniedException {
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
        product.setCost(5000D);
        product.setName("qwertz");

        ProductTO addedProduct = productService.addProduct(product);

        product.setCost(5001D);
        ProductTO addedProduct2 = productService.addProduct(product);

        List<Long> products = new LinkedList<>();
        products.add(addedProduct.getId());

        TransactionTO transaction = new TransactionTO();
        transaction.setDate(new Date());
        transaction.setStatus(Status.WAITING_FOR_PAYMENT);
        transaction.setProducts(products);
        transaction.setClient(addedClient.getId());
        transaction.setQuantity(products.size());

        //when
        transactionService.addTransaction(transaction);
        transactionService.addTransaction(transaction);
        transactionService.addTransaction(transaction);
        products.add(addedProduct2.getId());
        transaction.setProducts(products);
        transactionService.addTransaction(transaction);

        //then
        assertThat(clientService.findClientById(addedClient.getId()).getTransactions().size()).isEqualTo(4);
    }


    @Test
    @Transactional
    public void shouldThrowExceptionBecauseTooManyExpensiveProducts() throws TransactionDeniedException {
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
        product.setCost(3001D);
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


        transactionService.addTransaction(transaction);
        transactionService.addTransaction(transaction);
        transactionService.addTransaction(transaction);
        transactionService.addTransaction(transaction);

        product.setCost(7001D);
        ProductTO addedProduct2 = productService.addProduct(product);

        products.add(addedProduct2.getId());
        products.add(addedProduct2.getId());
        products.add(addedProduct2.getId());
        products.add(addedProduct2.getId());
        products.add(addedProduct2.getId());
        products.add(addedProduct2.getId());

        transaction.setProducts(products);

        //when
        boolean exceptionThrown = false;
        try {
            transactionService.addTransaction(transaction);
        } catch (TransactionDeniedException e){
            exceptionThrown = true;
        }

        //then
        assertTrue(exceptionThrown);
    }
}
