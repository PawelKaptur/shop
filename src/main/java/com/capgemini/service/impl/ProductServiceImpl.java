package com.capgemini.service.impl;

import com.capgemini.entity.ProductEntity;
import com.capgemini.entity.TransactionEntity;
import com.capgemini.mapper.ProductMapper;
import com.capgemini.repository.ProductRepository;
import com.capgemini.repository.TransactionRepository;
import com.capgemini.service.ProductService;
import com.capgemini.type.ProductTO;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private TransactionRepository transactionRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, TransactionRepository transactionRepository) {
        this.productRepository = productRepository;
        this.transactionRepository = transactionRepository;
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
    @Transactional(readOnly = false)
    public void removeProduct(Long id) {
        ProductEntity product = productRepository.findProductEntityById(id);
        removeProductFromTransactions(product);
        productRepository.deleteById(id);
    }

    private void removeProductFromTransactions(ProductEntity product) {
        List<TransactionEntity> transactions = product.getTransactions();
        for (TransactionEntity transaction : transactions) {
            List<ProductEntity> products = transaction.getProducts();
            for (ProductEntity productEntity : products) {
                if (productEntity.getId() == product.getId()) {
                    products.remove(productEntity);
                }
            }
            transaction.setProducts(products);
        }
        transactionRepository.saveAll(transactions);
    }

    @Override
    public List<ProductTO> findAllProducts() {
        return ProductMapper.toProductTOList(productRepository.findAll());
    }

    @Override
    public ProductTO updateProduct(ProductTO product) {
        ProductEntity productEntity = productRepository.findProductEntityById(product.getId());
        productEntity.setWeight(product.getWeight());
        productEntity.setMargin(product.getMargin());
        productEntity.setCost(product.getCost());
        productEntity.setName(product.getName());

        productRepository.save(productEntity);

        return ProductMapper.toProductTO(productEntity);
    }

    @Override
    public List<ProductTO> findTenBestSellers() {
        return ProductMapper.toProductTOList(productRepository.findTenBestSellers());
    }
}
