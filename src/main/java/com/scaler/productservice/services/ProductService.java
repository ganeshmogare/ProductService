package com.scaler.productservice.services;

import com.scaler.productservice.exceptions.ProductNotFound;
import com.scaler.productservice.models.Product;

import java.util.ArrayList;
import java.util.List;

public interface ProductService {
    Product createProduct(Product product);

    ArrayList<Product> getAllProducts();

    Product getProduct(Long id);

    String deleteProduct(Long id) throws ProductNotFound;

    Product updateProduct(Long id, Product product) throws ProductNotFound;

    Product replaceProduct(Long id, Product product) throws ProductNotFound;
}
