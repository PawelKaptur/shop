package com.capgemini.mapper;

import com.capgemini.entity.ProductEntity;
import com.capgemini.type.ProductTO;

import java.util.stream.Collectors;

public class ProductMapper {
    public static ProductTO toProductTO(ProductEntity productEntity) {
        if (productEntity == null) {
            return null;
        }

        ProductTO productTO = new ProductTO();
        productTO.setCost(productEntity.getCost());
        productTO.setId(productEntity.getId());
        productTO.setMargin(productEntity.getMargin());
        productTO.setWeight(productEntity.getWeight());
        if (productEntity.getTransactions() != null) {
            productTO.setTransactions(productEntity.getTransactions().stream().map(t -> t.getId()).collect(Collectors.toList()));
        }

        return productTO;
    }

    public static ProductEntity toProductEntity(ProductTO productTO) {
        if (productTO == null) {
            return null;
        }

        ProductEntity productEntity = new ProductEntity();
        productEntity.setCost(productTO.getCost());
        productEntity.setMargin(productTO.getMargin());
        productEntity.setWeight(productTO.getWeight());

        return productEntity;
    }
}
