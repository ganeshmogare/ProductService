package com.scaler.productservice.services;

import com.scaler.productservice.models.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("dbService")
//@Primary
public class ProductServiceDBImplementation implements ProductService{
    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public ArrayList<Product> getAllProducts() {
        return null;
    }

    @Override
    public Product getProduct(Long id) {
        return null;
    }
}
