package com.capgemini.repository.impl;

import com.capgemini.entity.QProductEntity;
import com.capgemini.entity.QTransactionEntity;
import com.capgemini.repository.custom.TransactionRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

public class TransactionRepositoryImpl implements TransactionRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    JPAQueryFactory queryFactory;
    QTransactionEntity transaction;
    QProductEntity product;

    private void initialiation() {
        queryFactory = new JPAQueryFactory(this.entityManager);
        transaction = QTransactionEntity.transactionEntity;
        product = QProductEntity.productEntity;
    }

    @Override
    public Double calculateProfitBetween(Date startDate, Date endDate) {
        initialiation();

        return queryFactory.selectFrom(transaction)
                .select(product.cost.multiply(product.margin).sum())
                .innerJoin(transaction.products, product)
                .where(transaction.date.between(startDate, endDate))
                .fetchOne();
    }
}
