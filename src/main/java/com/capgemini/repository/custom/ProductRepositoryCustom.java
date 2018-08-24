package com.capgemini.repository.custom;

import com.capgemini.entity.ProductEntity;
import com.querydsl.core.Tuple;

import java.util.List;

public interface ProductRepositoryCustom {
    List<ProductEntity> findTenBestSellers();
    List<Tuple> findItemsInTransactionInRealization();
}
