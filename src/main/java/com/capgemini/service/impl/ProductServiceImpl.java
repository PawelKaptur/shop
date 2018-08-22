package com.capgemini.service.impl;

import com.capgemini.entity.ProductEntity;
import com.capgemini.mapper.ProductMapper;
import com.capgemini.repository.ProductRepository;
import com.capgemini.service.ProductService;
import com.capgemini.type.ProductTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductTO findProductById(Long id) {
        return ProductMapper.toProductTO(productRepository.findProductEntityById(id));
    }

    @Override
    @Transactional(readOnly = false)
    public ProductTO addProduct(ProductTO productTO) {
        ProductEntity productEntity = productRepository.save(ProductMapper.toProductEntity(productTO));
        return ProductMapper.toProductTO(productEntity);
    }

    @Override
    public void removeProduct(Long id) {

    }

    @Override
    public List<ProductTO> findAllProducts() {
        return null;
    }

    @Override
    public ProductTO updateProduct(ProductTO client) {
        return null;
    }
}
