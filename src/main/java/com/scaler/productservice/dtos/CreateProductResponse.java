package com.scaler.productservice.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.scaler.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateProductResponse {
    private Long id;
    private String title;
    private String description;

    public static CreateProductResponse fromProduct(Product product){
        CreateProductResponse res = new CreateProductResponse();
        res.setId(product.getId());
        res.setTitle(product.getTitle());
        res.setDescription(product.getDescription());

        return res;
    }
}
