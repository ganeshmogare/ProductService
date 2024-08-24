package com.scaler.productservice.dtos;

import com.scaler.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplaceProductResponse {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String imageUrl;
    private String categoryName;

    public static ReplaceProductResponse fromProduct(Product product){
        ReplaceProductResponse obj = new ReplaceProductResponse();
        obj.setId(product.getId());
        obj.setTitle(product.getTitle());
        obj.setDescription(product.getDescription());
        obj.setPrice(product.getPrice());
        obj.setImageUrl(product.getImageUrl());
        obj.setCategoryName(product.getCategory().getName());

        return obj;
    }
}
