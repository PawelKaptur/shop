package com.capgemini.repository.impl;

import com.capgemini.Status;
import com.capgemini.TransactionSearchCriteria;
import com.capgemini.entity.QProductEntity;
import com.capgemini.entity.QTransactionEntity;
import com.capgemini.entity.TransactionEntity;
import com.capgemini.repository.custom.TransactionRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<TransactionEntity> searchTransactionByCriteria(TransactionSearchCriteria criteria) {
        BooleanBuilder query = new BooleanBuilder();

        if (criteria.getLastName() != null) {
            query.and(transaction.client.lastName.eq(criteria.getLastName()));
        }

        if (criteria.getStartDate() != null && criteria.getEndDate() != null) {
            query.and(transaction.date.between(criteria.getStartDate(), criteria.getEndDate()));
        }

        if (criteria.getProductId() != null) {
            query.and(product.id.eq(criteria.getProductId()));
        }

        if (criteria.getCostOfTransaction() != null) {
            query.and(product.cost.sum().eq(criteria.getCostOfTransaction()));
        }

        if(criteria.getCostOfTransaction() == null) {
            return queryFactory.selectFrom(transaction)
                    .innerJoin(transaction.products, product)
                    .where(query)
                    .groupBy(transaction.id)
                    .fetch();
        }
        else{
        /*    List<Tuple> tuples = queryFactory.selectFrom(transaction)
                    .select(transaction)
                    .innerJoin(transaction.products, product)
                    .where(query)
                    .groupBy(transaction.id)
                    .fetch(); */

            return queryFactory.selectFrom(transaction)
                    .select(transaction)
                    .innerJoin(transaction.products, product)
                    .where(query)
                    .groupBy(transaction.id)
                    .fetch();

            //return tuples.stream().map(t -> (TransactionEntity) t.toArray()[0]).collect(Collectors.toList());
        }
    }
}
