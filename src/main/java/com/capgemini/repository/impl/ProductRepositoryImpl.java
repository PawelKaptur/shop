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
import java.util.stream.Collectors;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    JPAQueryFactory queryFactory;
    QTransactionEntity transaction;
    QProductEntity product;

    @PostConstruct
    private void initialiation() {
        queryFactory = new JPAQueryFactory(this.entityManager);
        transaction = QTransactionEntity.transactionEntity;
        product = QProductEntity.productEntity;
    }

    @Override
    public List<ProductEntity> findTenBestSellers() {
        NumberPath<Long> count = Expressions.numberPath(Long.class, "c");
        List<Tuple> tuples = queryFactory.selectFrom(product)
                .select(product, product.id.count().as(count))
                .innerJoin(product.transactions, transaction)
                .groupBy(product.id)
                .orderBy(count.desc())
                .limit(10L)
                .fetch();

        List<ProductEntity> products = tuples.stream().map(t -> (ProductEntity) t.toArray()[0]).collect(Collectors.toList());

        return products;
    }

    @Override
    public List<Tuple> findItemsInTransactionInRealization() {
        NumberPath<Long> count = Expressions.numberPath(Long.class, "c");
        return queryFactory.selectFrom(product)
                .select(product.name, product.id.count().as(count))
                .innerJoin(product.transactions, transaction)
                .where(transaction.status.eq(Status.IN_REALIZATION))
                .groupBy(product.id)
                .fetch();
    }
}
