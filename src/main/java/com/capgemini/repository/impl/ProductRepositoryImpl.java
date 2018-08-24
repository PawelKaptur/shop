package com.capgemini.repository.impl;

import com.capgemini.Status;
import com.capgemini.entity.QProductEntity;
import com.capgemini.entity.QTransactionEntity;
import com.capgemini.repository.custom.ProductRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Tuple> findTenBestSellers() {
        QTransactionEntity transaction = QTransactionEntity.transactionEntity;
        QProductEntity product = QProductEntity.productEntity;
        JPAQueryFactory queryFactory = new JPAQueryFactory(this.entityManager);

        //to znajduje liste transakcji po id klienta
        //queryFactory.selectFrom(transaction).where(transaction.client.id.eq(id)).fetch()

        NumberPath<Long> count = Expressions.numberPath(Long.class, "c");

        //List<ProductEntity> products = queryFactory.selectFrom(product).innerJoin(transaction).groupBy(product.id)

       /* return queryFactory.selectFrom(product).select(product, product.id.count().as(count))
                .innerJoin(product.transactions, transaction)
                .groupBy(product.id)
                .fetch();*/
       return  queryFactory.selectFrom(product)
               .select(product, product.id.count().as(count))
               .innerJoin(product.transactions, transaction)
               .groupBy(product.id)
               .orderBy(count.desc())
               .limit(10L)
               .fetch();
/*        return  queryFactory.selectFrom(product)
                .where(product.id.in(
                        JPAExpressions.select(product, product.id.count().as(count))
                                .innerJoin(product.transactions, transaction)
                                .groupBy(product.id)
                                .limit(10L)
                ))
                .fetch();*/
    }


    @Override
    public List<Tuple> findItemsInTransactionInRealization() {
        QTransactionEntity transaction = QTransactionEntity.transactionEntity;
        QProductEntity product = QProductEntity.productEntity;
        JPAQueryFactory queryFactory = new JPAQueryFactory(this.entityManager);

        NumberPath<Long> count = Expressions.numberPath(Long.class, "c");
        return queryFactory.selectFrom(product)
                .select(product.name, product.id.count().as(count))
                .innerJoin(product.transactions, transaction)
                .where(transaction.status.eq(Status.IN_REALIZATION))
                .groupBy(product.id)
                .fetch();
    }
}
