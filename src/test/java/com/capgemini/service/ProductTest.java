package com.capgemini.service;


import com.capgemini.type.ProductTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class ProductTest {

    @Autowired
    private ProductService productService;

    @Test
    @Transactional
    public void shouldAddProduct(){
        //given
        ProductTO product = new ProductTO();
        product.setWeight(2D);
        product.setMargin(0.2);
        product.setCost(1000D);

        ProductTO addedProduct = productService.addProduct(product);

        //when
        ProductTO selectedProduct = productService.findProductById(addedProduct.getId());

        //then
        assertThat(selectedProduct.getId()).isEqualTo(addedProduct.getId());
    }
}
