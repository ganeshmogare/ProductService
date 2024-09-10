package com.scaler.productservice.services.sortingService;

import com.scaler.productservice.models.Product;

import java.util.List;

public interface Sorter {
    List<Product> sort(List<Product> products);
}
