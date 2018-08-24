package com.capgemini.repository.impl;

import com.capgemini.entity.QProductEntity;
import com.capgemini.entity.QTransactionEntity;
import com.capgemini.entity.TransactionEntity;
import com.capgemini.repository.custom.TransactionRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

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
    public List<TransactionEntity> profitBetween(Date startDate, Date endDate) {
        initialiation();

        return queryFactory.selectFrom(transaction)
                .where(transaction.date.between(startDate, endDate))
                .fetch();
    }
}
