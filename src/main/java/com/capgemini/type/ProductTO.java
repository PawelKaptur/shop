package com.capgemini.type;

import lombok.Data;

import java.util.List;

@Data
public class ProductTO {
    private Long id;
    private String name;
    private Double cost;
    private Double margin;
    private Double weight;
    private List<Long> transactions;
}
