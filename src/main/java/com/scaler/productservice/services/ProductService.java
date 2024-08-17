package com.scaler.productservice.services;

import com.scaler.productservice.models.Product;

import java.util.ArrayList;
import java.util.List;

public interface ProductService {
    Product createProduct(Product product);

    ArrayList<Product> getAllProducts();

    Product getProduct(Long id);

    void deleteProduct(Long id);

    Product updateProduct(Long id, Product product);

    String replaceProduct(Long id, Product product);
}
