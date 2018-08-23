package com.capgemini.repository.impl;

import com.capgemini.entity.*;
import com.capgemini.repository.custom.ProductRepositoryCustom;
import com.capgemini.type.ProductTO;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<ProductTO> findTenBestSellers() {
        QTransactionEntity transaction = QTransactionEntity.transactionEntity;
        QProductEntity product = QProductEntity.productEntity;

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.entityManager);

        //List<ProductEntity> products = queryFactory.selectFrom(product).innerJoin(transaction).groupBy(product.id)

       return null;
    }
}
