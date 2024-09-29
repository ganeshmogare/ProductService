package com.scaler.productservice.controllers;

import com.scaler.productservice.dtos.*;
import com.scaler.productservice.exceptions.ProductNotFound;
import com.scaler.productservice.models.Product;
import com.scaler.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products/")
public class ProductController {

//    @Value("${productService}")
//    private String ProductServiceType;

//    @Qualifier()
    private ProductService productService;
    private RestTemplate restTemplate;

    ProductController(@Qualifier("dbService") ProductService productService, RestTemplate restTemplate){
        this.productService = productService;
        this.restTemplate = restTemplate;
    }

    @PostMapping("")
    public CreateProductResponse createProduct(@RequestHeader("Authorization") String token,@RequestBody CreateProductRequest CreateProductData ){
        boolean isAuthenticated = restTemplate.getForObject(
                "http://UserService/auth/validate?token=" + token,
                Boolean.class
        );

        if (!isAuthenticated) {
            return null;
        }
        Product product =  productService.createProduct(CreateProductData.toProduct());

        return CreateProductResponse.fromProduct(product);
    }

    @PostMapping("test_create")
    public CreateProductResponse createProductForTest(@RequestBody CreateProductRequest CreateProductData ){
        Product product =  productService.createProduct(CreateProductData.toProduct());

        return CreateProductResponse.fromProduct(product);
    }

    @GetMapping("")
    public GetAllProductsResponse getAllProducts(){
        ArrayList<Product> products = productService.getAllProducts();

        return GetAllProductsResponse.fromProducts(products);
    }

    @GetMapping("/{id}")
    public GetProductResponse getSingleProduct(@PathVariable("id")Long id){

        if (id < 0) {
            throw new RuntimeException("Product not found");
        } else if(id == 0) {
            throw new RuntimeException("Something very bad");
        }

        Product product = productService.getProduct(id);

        return GetProductResponse.fromProduct(product);
    }

    @DeleteMapping("{id}")
    public String deleteProduct(@PathVariable("id") Long id) throws ProductNotFound {
        productService.deleteProduct(id);

        return "Product with id "+ id + " is deleted successfully!!!";
    }

    @PatchMapping("{id}")
    public UpdateProductResponse updateProduct(@RequestBody UpdateProductRequest updateProductData, @PathVariable("id") Long id) throws ProductNotFound {
        Product product = productService.updateProduct(id, updateProductData.toProduct());
        return UpdateProductResponse.fromProduct(product);
    }


    @PutMapping("{id}")
    public Product replaceProduct(@RequestBody ReplaceProductRequest replaceProductData, @PathVariable("id") Long id) throws ProductNotFound {
        return productService.replaceProduct(id, replaceProductData.toProduct());
        //return ReplaceProductResponse.fromProduct(product);
    }

    //custom mapping
    @RequestMapping(name ="GANESH" , value = "")
    public String getMeEverything(){
      return "Hello!! You are calling custom http request method";
    }
}
