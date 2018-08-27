package com.capgemini.service;

import com.capgemini.type.ProductTO;
import com.querydsl.core.Tuple;

import java.util.List;

public interface ProductService {
    ProductTO findProductById(Long id);

    ProductTO addProduct(ProductTO product);

    void removeProduct(Long id);

    List<ProductTO> findAllProducts();

    ProductTO updateProduct(ProductTO client);

    List<ProductTO> findTenBestSellers();
}
