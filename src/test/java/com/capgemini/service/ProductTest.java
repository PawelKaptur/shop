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

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class ProductTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;

    @Test
    @Transactional
    public void shouldAddProduct() {
        //given
        ProductTO product = new ProductTO();
        product.setWeight(2D);
        product.setMargin(0.2);
        product.setCost(1000D);
        product.setName("qwertz");

        ProductTO addedProduct = productService.addProduct(product);

        //when
        ProductTO selectedProduct = productService.findProductById(addedProduct.getId());

        //then
        assertThat(selectedProduct.getId()).isEqualTo(addedProduct.getId());
    }

    @Test
    @Transactional
    public void shouldRemoveProduct() {
        //given
        ProductTO product = new ProductTO();
        product.setWeight(2D);
        product.setMargin(0.2);
        product.setCost(1000D);
        product.setName("qwertz");

        ProductTO addedProduct = productService.addProduct(product);

        //when
        productService.removeProduct(addedProduct.getId());

        //then
        assertThat(productService.findProductById(addedProduct.getId())).isNull();
    }

    @Test
    @Transactional
    public void shouldFindThreeProducts() {
        //given
        ProductTO product = new ProductTO();
        product.setWeight(2D);
        product.setMargin(0.2);
        product.setCost(1000D);
        product.setName("qwertz");

        productService.addProduct(product);
        productService.addProduct(product);
        productService.addProduct(product);

        //when
        List<ProductTO> products = productService.findAllProducts();

        //then
        assertThat(products.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    public void shouldUpdateWeight() {
        //given
        ProductTO product = new ProductTO();
        product.setWeight(2D);
        product.setMargin(0.2);
        product.setCost(1000D);
        product.setName("qwertz");

        ProductTO addedProduct = productService.addProduct(product);

        Double weight = 100D;

        //when
        addedProduct.setWeight(weight);
        ProductTO updatedProduct = productService.updateProduct(addedProduct);

        //then
        assertThat(updatedProduct.getWeight().equals(weight));
        assertThat(updatedProduct.getId()).isEqualTo(addedProduct.getId());
        assertThat(productService.findProductById(updatedProduct.getId()).getWeight()).isEqualTo(weight);
    }

    @Test
    @Transactional
    public void shouldRemoveProductWithTransaction() throws TransactionDeniedException {
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

        //when
        TransactionTO addedTransaction = transactionService.addTransaction(transaction);
        productService.removeProduct(addedProduct.getId());

        //then
        assertThat(productService.findProductById(addedProduct.getId())).isNull();
        assertThat(transactionService.findTransactionById(addedTransaction.getId()).getProducts().contains(addedProduct.getId())).isFalse();
        assertThat(transactionService.findTransactionById(addedTransaction.getId()).getProducts().contains(addedProduct2.getId())).isTrue();
    }
}
