package com.capgemini.repository.impl;

import com.capgemini.Status;
import com.capgemini.entity.*;
import com.capgemini.repository.custom.ClientRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ClientRepositoryImpl implements ClientRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public List<ClientEntity> findClientsByLastName(String lastName) {
        QClientEntity client = QClientEntity.clientEntity;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        List<ClientEntity> clients = queryFactory.selectFrom(client)
                .where(client.lastName.eq(lastName))
                .fetch();

        return clients;
    }


    @Override
    public Double costOfAllTransactionsForClient(Long id) {
        QClientEntity client = QClientEntity.clientEntity;
        QTransactionEntity transaction = QTransactionEntity.transactionEntity;
        QProductEntity product = QProductEntity.productEntity;

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.entityManager);

        //Double cost = queryFactory.selectFrom(client).innerJoin(client.transactions, transaction).where(client.id.eq(id)).fetchOne();
        //Double cost = queryFactory.select(product.cost.sum().as("sum")).select()


        return null;
        //return queryFactory.selectFrom(transaction).where(transaction.client.id.eq(id));
    }


    @Override
    public List<Tuple> fffff() {
        QClientEntity client = QClientEntity.clientEntity;
        QTransactionEntity transaction = QTransactionEntity.transactionEntity;
        QProductEntity product = QProductEntity.productEntity;

        JPAQueryFactory queryFactory = new JPAQueryFactory(this.entityManager);

        //to znajduje liste transakcji po id klienta
        //queryFactory.selectFrom(transaction).where(transaction.client.id.eq(id)).fetch()

        //probuje f to nizej nawet niezle dziala znajduje te itemki ale nie podlicza
        //return queryFactory.selectFrom(product).innerJoin(product.transactions, transaction).where(transaction.status.eq(Status.IN_REALIZATION)).groupBy(product.id).fetch();

        NumberPath<Long> count = Expressions.numberPath(Long.class, "c");
        return queryFactory.selectFrom(product).select(product.name, product.id.count().as(count)).innerJoin(product.transactions, transaction).where(transaction.status.eq(Status.IN_REALIZATION)).groupBy(product.id).fetch();
    }

}
