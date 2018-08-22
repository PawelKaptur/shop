package com.capgemini.service;


import com.capgemini.Status;
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
public class TransactionTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProductService productService;

    @Test
    @Transactional
    public void shouldAddtransaction(){
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
        TransactionTO selectedTransaction = transactionService.findTransactionById(addedTransaction.getId());

        //then
        assertThat(selectedTransaction.getId()).isEqualTo(selectedTransaction.getId());
        assertThat(clientService.findClientById(addedClient.getId()).getTransactions().size()).isEqualTo(1);
        assertThat(clientService.findClientById(addedClient.getId()).getTransactions().get(0)).isEqualTo(addedTransaction.getId());
    }
}
