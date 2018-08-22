package com.capgemini.mapper;

import com.capgemini.entity.TransactionEntity;
import com.capgemini.type.TransactionTO;

import java.util.stream.Collectors;

public class TransactionMapper {
    public static TransactionTO toTransactionTO(TransactionEntity transactionEntity){
        if(transactionEntity == null){
            return null;
        }

        TransactionTO transactionTO = new TransactionTO();
        transactionTO.setId(transactionEntity.getId());
        transactionTO.setDate(transactionEntity.getDate());
        transactionTO.setQuantity(transactionEntity.getQuantity());
        transactionTO.setStatus(transactionEntity.getStatus());
        transactionTO.setClient(transactionEntity.getClient().getId());
        transactionTO.setProducts(transactionEntity.getProducts().stream().map(p -> p.getId()).collect(Collectors.toList()));

        return transactionTO;
    }

    public static TransactionEntity toTransactionEntity(TransactionTO transactionTO){
        if(transactionTO == null){
            return null;
        }

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setDate(transactionTO.getDate());
        transactionEntity.setQuantity(transactionTO.getQuantity());
        transactionEntity.setStatus(transactionTO.getStatus());

        return transactionEntity;
    }
}
