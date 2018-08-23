package com.capgemini.repository;

import com.capgemini.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    TransactionEntity findTransactionEntityById(Long id);
}
