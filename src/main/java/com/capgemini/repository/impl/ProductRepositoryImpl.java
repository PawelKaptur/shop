package com.capgemini.repository.impl;

import com.capgemini.Status;
import com.capgemini.entity.ProductEntity;
import com.capgemini.entity.QProductEntity;
import com.capgemini.entity.QTransactionEntity;
import com.capgemini.repository.custom.ProductRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private JPAQueryFactory queryFactory;
    private QTransactionEntity transaction;
    private QProductEntity product;

    @PostConstruct
    private void initialiation() {
        queryFactory = new JPAQueryFactory(this.entityManager);
        transaction = QTransactionEntity.transactionEntity;
        product = QProductEntity.productEntity;
    }

    @Override
    public List<ProductEntity> findTenBestSellers() {
        return queryFactory.selectFrom(transaction)
                .select(product)
                .innerJoin(transaction.products, product)
                .groupBy(product.id)
                .orderBy(product.id.count().desc())
                .limit(10L)
                .fetch();
    }

    @Override
    public List<Tuple> findItemsInTransactionInRealization() {
        NumberPath<Long> count = Expressions.numberPath(Long.class, "c");
        return queryFactory.selectFrom(transaction)
                .select(product.name, product.id.count().as(count))
                .join(transaction.products, product)
                .where(transaction.status.eq(Status.IN_REALIZATION))
                .groupBy(product.id)
                .fetch();
    }
}
