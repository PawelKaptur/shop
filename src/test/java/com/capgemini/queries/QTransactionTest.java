package com.capgemini.queries;

import com.capgemini.Status;
import com.capgemini.entity.TransactionEntity;
import com.capgemini.exception.TransactionDeniedException;
import com.capgemini.repository.TransactionRepository;
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
public class QTransactionTest {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProductService productService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    @Transactional
    public void shouldFindProfit() throws TransactionDeniedException {
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

        product.setCost(1500D);
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
        transaction.setDate(new Date(2000L));
        transaction.setClient(addedClient2.getId());
        transactionService.addTransaction(transaction);

        //when
        Double profit = transactionRepository.calculateProfitBetween(new Date(500L), new Date(2500L));
        Double profit2 = transactionRepository.calculateProfitBetween(new Date(500L), new Date(1500L));
        Double profit3 = transactionRepository.calculateProfitBetween(new Date(1500L), new Date(2500L));
        Double profit4 = transactionRepository.calculateProfitBetween(new Date(2001L), new Date(2500L));

        //then
        assertThat(profit).isEqualTo(700D);
        assertThat(profit2).isEqualTo(350D);
        assertThat(profit3).isEqualTo(350D);
        assertThat(profit4).isNull();
    }
}
