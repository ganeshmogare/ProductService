package com.scaler.productservice.dtos;

import com.scaler.productservice.models.Category;
import com.scaler.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplaceProductRequest {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String imageUrl;
    private String categoryName;

    public Product toProduct(){
        Product obj = new Product();
        obj.setId(this.getId());
        obj.setTitle(this.getTitle());
        obj.setDescription(this.getDescription());
        obj.setPrice(this.getPrice());
        obj.setImageUrl(this.getImageUrl());
        Category category = new Category();
        category.setName(this.getCategoryName());
        obj.setCategory(category);

        return obj;
    }
}
