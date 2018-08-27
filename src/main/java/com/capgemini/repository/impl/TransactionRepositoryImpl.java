package com.capgemini.repository.impl;

import com.capgemini.Status;
import com.capgemini.entity.QClientEntity;
import com.capgemini.entity.QProductEntity;
import com.capgemini.entity.QTransactionEntity;
import com.capgemini.repository.custom.TransactionRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

public class TransactionRepositoryImpl implements TransactionRepositoryCustom {
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
    public Double calculateProfitBetween(Date startDate, Date endDate) {
        return queryFactory.selectFrom(transaction)
                .select(product.cost.multiply(product.margin).sum())
                .innerJoin(transaction.products, product)
                .where(transaction.date.between(startDate, endDate))
                .fetchOne();
    }

    @Override
    public Double calculateAllCostOfTransactionsForClient(Long id) {
        return queryFactory.selectFrom(transaction)
                .select((product.cost).sum())
                .innerJoin(transaction.products, product)
                .where(transaction.client.id.eq(id))
                .fetchOne();
    }

    @Override
    public Double calculateAllCostOfTransactionsWithStatusForClient(Long id, Status status) {
        return queryFactory.selectFrom(transaction)
                .select((product.cost).sum())
                .innerJoin(transaction.products, product)
                .where(transaction.client.id.eq(id).and(transaction.status.eq(status)))
                .fetchOne();
    }

    @Override
    public Double calculateAllCostOfTransactionsWithStatusForAllClients(Status status) {
        return queryFactory.selectFrom(transaction)
                .select(product.cost.sum())
                .innerJoin(transaction.products, product)
                .where(transaction.status.eq(status))
                .fetchOne();
    }
}
