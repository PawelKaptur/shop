package com.capgemini.repository.impl;

import com.capgemini.entity.ClientEntity;
import com.capgemini.entity.QClientEntity;
import com.capgemini.entity.QProductEntity;
import com.capgemini.entity.QTransactionEntity;
import com.capgemini.repository.custom.ClientRepositoryCustom;
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
}
