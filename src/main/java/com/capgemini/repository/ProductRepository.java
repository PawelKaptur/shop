package com.capgemini.repository;

import com.capgemini.entity.ProductEntity;
import com.capgemini.repository.custom.ProductRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>, ProductRepositoryCustom {
    ProductEntity findProductEntityById(Long id);
}
