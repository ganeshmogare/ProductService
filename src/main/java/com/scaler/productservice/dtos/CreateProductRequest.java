package com.scaler.productservice.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.scaler.productservice.models.Category;
import com.scaler.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateProductRequest {
    private String title;
    private String description;
    private Double price;
    private String imageUrl;
    private String category;

    public Product toProduct(){
        Product obj = new Product();
        obj.setTitle(this.getTitle());
        obj.setDescription(this.getDescription());
        obj.setPrice(this.getPrice());
        obj.setImageUrl(this.getImageUrl());

        Category category = new Category();
        category.setName(this.getCategory());
        obj.setCategory(category);

        return obj;
    }
}
