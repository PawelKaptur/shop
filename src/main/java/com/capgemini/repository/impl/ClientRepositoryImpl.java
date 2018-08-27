package com.capgemini.repository.impl;

import com.capgemini.entity.ClientEntity;
import com.capgemini.entity.QClientEntity;
import com.capgemini.entity.QProductEntity;
import com.capgemini.entity.QTransactionEntity;
import com.capgemini.repository.custom.ClientRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

public class ClientRepositoryImpl implements ClientRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private JPAQueryFactory queryFactory;
    private QTransactionEntity transaction;
    private QClientEntity client;
    private QProductEntity product;

    @PostConstruct
    private void initialiation() {
        queryFactory = new JPAQueryFactory(this.entityManager);
        transaction = QTransactionEntity.transactionEntity;
        client = QClientEntity.clientEntity;
        product = QProductEntity.productEntity;
    }

    @Override
    public List<ClientEntity> findClientsByLastName(String lastName) {
        return queryFactory.selectFrom(client)
                .where(client.lastName.eq(lastName))
                .fetch();
    }

    @Override
    public List<ClientEntity> findThreeBestClientsBetween(Date startDate, Date endDate) {
        return queryFactory.selectFrom(client)
                .select(client)
                .innerJoin(client.transactions, transaction)
                .innerJoin(transaction.products, product)
                .groupBy(client.id)
                .where(transaction.date.between(startDate, endDate))
                .orderBy(product.cost.sum().desc())
                .limit(3)
                .fetch();
    }
}
