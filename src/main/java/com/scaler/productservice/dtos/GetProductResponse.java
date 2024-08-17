package com.scaler.productservice.dtos;

import com.scaler.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GetProductResponse {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private Double price;
    private String categoryName;

    public static GetProductResponse fromProduct(Product product){
     GetProductResponse response = new GetProductResponse();
     response.setId(product.getId());
     response.setTitle(product.getTitle());
     response.setPrice(product.getPrice());
     response.setImageUrl(product.getImageUrl());
     response.setCategoryName(product.getCategoryName());
     response.setDescription(product.getDescription());

     return response;
    }
}
