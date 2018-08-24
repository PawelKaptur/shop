package com.capgemini.repository.custom;

import com.querydsl.core.Tuple;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Tuple> findTenBestSellers();
    List<Tuple> findItemsInTransactionInRealization();
}
