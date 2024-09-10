package com.scaler.productservice.services.sortingService;

import com.scaler.productservice.models.Product;

import java.util.List;

public class RatingLowToHigh implements Sorter{
    @Override
    public List<Product> sort(List<Product> products) {
        return List.of();
    }
}
