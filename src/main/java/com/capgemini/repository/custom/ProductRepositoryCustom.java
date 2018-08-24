package com.capgemini.repository.custom;

import com.capgemini.type.ProductTO;
import com.querydsl.core.Tuple;

import java.util.List;

public interface ProductRepositoryCustom {
    List<ProductTO> findTenBestSellers();
    List<Tuple> findItemsInTransactionInRealization();

}
