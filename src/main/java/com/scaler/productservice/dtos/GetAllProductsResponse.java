package com.scaler.productservice.dtos;

import com.scaler.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Getter
@Setter
public class GetAllProductsResponse {
    private ArrayList<GetProductResponse> products = new ArrayList<GetProductResponse>();

    private void addProduct(GetProductResponse product){
        this.products.add(product);
    }

    public static GetAllProductsResponse fromProducts(ArrayList<Product> products){
        GetAllProductsResponse result = new GetAllProductsResponse();

        for(int i=0;i<products.size();i++){
            GetProductResponse response = new GetProductResponse();

            Product product = products.get(i);
            response.setId(product.getId());
            response.setTitle(product.getTitle());
            response.setPrice(product.getPrice());
            response.setImageUrl(product.getImageUrl());
            response.setCategoryName(product.getCategoryName());
            response.setDescription(product.getDescription());

            result.addProduct(response);
        }


        return result;
    }
}
