package com.capgemini.repository;

import com.capgemini.entity.ClientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<ClientEntity, Long> {
    ClientEntity findClientEntityById(Long id);
}
