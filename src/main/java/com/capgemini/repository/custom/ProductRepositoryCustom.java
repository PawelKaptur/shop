package com.capgemini.repository.custom;

import com.capgemini.type.ProductTO;

import java.util.List;

public interface ProductRepositoryCustom {
    List<ProductTO> findTenBestSellers();
}
