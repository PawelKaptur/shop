package com.capgemini.repository;

import com.capgemini.entity.ClientEntity;
import com.capgemini.repository.custom.ClientRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long>, ClientRepositoryCustom {
    ClientEntity findClientEntityById(Long id);
}
