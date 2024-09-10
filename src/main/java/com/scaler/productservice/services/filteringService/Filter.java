package com.scaler.productservice.services.filteringService;

import com.scaler.productservice.models.Product;

import java.util.List;

public interface Filter {
    public List<Product> apply(List<Product> products, List<String> allowedValues);
}
