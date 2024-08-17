package com.scaler.productservice.dtos;

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
}
