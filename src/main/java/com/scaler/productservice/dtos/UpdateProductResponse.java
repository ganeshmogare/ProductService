package com.scaler.productservice.dtos;

import com.scaler.productservice.models.Category;
import com.scaler.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductResponse {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String imageUrl;
    private String category;

    public static UpdateProductResponse fromProduct(Product product){
        UpdateProductResponse obj = new UpdateProductResponse();
        obj.setId(product.getId());
        obj.setTitle(product.getTitle());
        obj.setDescription(product.getDescription());
        obj.setPrice(product.getPrice());
        obj.setImageUrl(product.getImageUrl());
        obj.setCategory(product.getCategory().getName());

        return obj;
    }
}
