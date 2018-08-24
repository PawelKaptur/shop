package com.capgemini.repository;

import com.capgemini.entity.TransactionEntity;
import com.capgemini.repository.custom.TransactionRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long>, TransactionRepositoryCustom {
    TransactionEntity findTransactionEntityById(Long id);
}
