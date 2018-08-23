package com.capgemini.queries;

import com.capgemini.Status;
import com.capgemini.exception.TransactionDeniedException;
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
public class QProductTest {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProductService productService;

    @Autowired
    private TransactionService transactionService;

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
        ClientTO addedClient2 = clientService.addClient(client);

        ProductTO product = new ProductTO();
        product.setWeight(2D);
        product.setMargin(0.2);
        product.setCost(1D);

        ProductTO addedProduct = productService.addProduct(product);
        ProductTO addedProduct2 = productService.addProduct(product);
        ProductTO addedProduct3 = productService.addProduct(product);
        ProductTO addedProduct4 = productService.addProduct(product);
        ProductTO addedProduct5 = productService.addProduct(product);
        ProductTO addedProduct6 = productService.addProduct(product);
        ProductTO addedProduct7 = productService.addProduct(product);
        ProductTO addedProduct8 = productService.addProduct(product);
        ProductTO addedProduct9 = productService.addProduct(product);
        ProductTO addedProduct10 = productService.addProduct(product);
        ProductTO addedProduct11 = productService.addProduct(product);
        ProductTO addedProduct12 = productService.addProduct(product);
        ProductTO addedProduct13 = productService.addProduct(product);
        ProductTO addedProduct14 = productService.addProduct(product);

        List<Long> products = new LinkedList<>();
        products.add(addedProduct.getId());
        products.add(addedProduct2.getId());
        products.add(addedProduct3.getId());
        products.add(addedProduct14.getId());
        products.add(addedProduct13.getId());
        products.add(addedProduct12.getId());
        products.add(addedProduct11.getId());
        products.add(addedProduct10.getId());
        products.add(addedProduct9.getId());
        products.add(addedProduct8.getId());

        List<Long> products2 = new LinkedList<>();
        products2.add(addedProduct4.getId());
        products2.add(addedProduct5.getId());
        products2.add(addedProduct6.getId());
        products2.add(addedProduct7.getId());

        TransactionTO transaction = new TransactionTO();
        transaction.setDate(new Date());
        transaction.setStatus(Status.WAITING_FOR_PAYMENT);
        transaction.setProducts(products);
        transaction.setClient(addedClient.getId());
        transaction.setQuantity(products.size());

        //when
        for(int i = 0; i < 15; i++){
            transactionService.addTransaction(transaction);
        }

        transaction.setClient(addedClient2.getId());
        transaction.setProducts(products2);
        for(int i = 0; i < 5; i++){
            transactionService.addTransaction(transaction);
        }

        List<ProductTO> bestProducts = productService.findTenBestSellers();

        //then
        assertThat(bestProducts.size()).isEqualTo(10);
        assertThat(bestProducts.stream().map(p -> p.getTransactions()).anyMatch(t -> t.contains(addedProduct11.getId()))).isTrue();
    }
}
