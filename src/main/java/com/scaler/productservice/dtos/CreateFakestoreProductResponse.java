package com.scaler.productservice.dtos;

import com.scaler.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateFakestoreProductResponse {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String imageUrl;
    private String category;

    public static CreateProductResponse fromProduct(Product product){
        CreateProductResponse res = new CreateProductResponse();
        res.setId(product.getId());
        res.setTitle(product.getTitle());
        res.setDescription(product.getDescription());

        return res;
    }
}
