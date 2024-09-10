package com.scaler.productservice.services.filteringService;

import com.scaler.productservice.models.Product;

import java.util.List;

public class RamFilter implements Filter{
    @Override
    public List<Product> apply(List<Product> products, List<String> allowedValues) {
        return List.of();
    }
}
