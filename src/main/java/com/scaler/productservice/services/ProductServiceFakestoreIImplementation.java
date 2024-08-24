package com.scaler.productservice.services;

import com.scaler.productservice.dtos.*;
import com.scaler.productservice.exceptions.ProductNotFound;
import com.scaler.productservice.models.Category;
import com.scaler.productservice.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreService")
public class ProductServiceFakestoreIImplementation implements ProductService{

    private RestTemplate restTemplate;

    public ProductServiceFakestoreIImplementation(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    public Product createProduct(Product product) {
        CreateFakestoreProductRequest request = new CreateFakestoreProductRequest();
        request.setTitle(product.getTitle());
        request.setDescription(product.getDescription());
        request.setPrice(request.getPrice());
        request.setImageUrl(product.getImageUrl());
        request.setCategory(product.getCategory().getName());
        CreateFakestoreProductResponse response =  restTemplate.postForObject("https://fakestoreapi.com/products",
                request,
                CreateFakestoreProductResponse.class);

        if(response == null) return null;

        return prepareProductObj(response);
    }

    @Override
    public ArrayList<Product> getAllProducts() {
        CreateFakestoreProductResponse[] response =  restTemplate.getForObject("https://fakestoreapi.com/products",
                CreateFakestoreProductResponse[].class);

        ArrayList<Product> result = new ArrayList<>();

        if(response == null) return null;

        for (int i=0;i<response.length;i++){

            result.add(prepareProductObj(response[i]));
        }


        return result;
    }

    private Product prepareProductObj(CreateFakestoreProductResponse response){
        Product res = new Product();
        res.setId(response.getId());
        res.setTitle(response.getTitle());
        res.setDescription(response.getDescription());
        res.setPrice(response.getPrice());
        res.setImageUrl(response.getImageUrl());
        Category category = new Category();
        category.setName(response.getCategory());
        res.setCategory(category);

        return res;
    }

    private Product prepareProductObjForGet(GetProductResponse response){
        Product res = new Product();
        res.setId(response.getId());
        res.setTitle(response.getTitle());
        res.setDescription(response.getDescription());
        res.setPrice(response.getPrice());
        res.setImageUrl(response.getImageUrl());
        Category category = new Category();
        category.setName(response.getCategory());
        res.setCategory(category);

        return res;
    }

    @Override
    public Product getProduct(Long id) {
        GetProductResponse response =  restTemplate.getForObject("https://fakestoreapi.com/products/{0}",
                GetProductResponse.class, id);

        if(response == null) return null;

        return prepareProductObjForGet(response);
    }

    @Override
    public String deleteProduct(Long id) throws ProductNotFound {
        if(id == null){
            throw new ProductNotFound();
        }
        restTemplate.delete("https://fakestoreapi.com/products/{0}", id);

        return "Product with id "+ id + " is deleted successfully!!!";
    };

    @Override
    public Product updateProduct(Long id,Product product) {
        UpdateProductResponse response = restTemplate.patchForObject("https://fakestoreapi.com/products/{0}", product, UpdateProductResponse.class, id);

        Product res = new Product();
        res.setId(response.getId());
        res.setTitle(response.getTitle());
        res.setDescription(response.getDescription());
        res.setPrice(response.getPrice());
        res.setImageUrl(response.getImageUrl());
        Category category = new Category();
        category.setName(response.getCategory());
        res.setCategory(category);

        return res;
    }

    @Override
    public String replaceProduct(Long id, Product product) {
        product.setId(id);
        restTemplate.put("https://fakestoreapi.com/products/{0}", product, id);

        return "Product replaced successfully";
    }
}
