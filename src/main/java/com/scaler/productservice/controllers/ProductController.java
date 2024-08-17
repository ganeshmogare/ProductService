package com.scaler.productservice.controllers;

import com.scaler.productservice.dtos.CreateProductRequest;
import com.scaler.productservice.dtos.CreateProductResponse;
import com.scaler.productservice.dtos.GetAllProductsResponse;
import com.scaler.productservice.dtos.GetProductResponse;
import com.scaler.productservice.models.Product;
import com.scaler.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products/")
public class ProductController {

//    @Value("${productService}")
//    private String ProductServiceType;

//    @Qualifier()
    private ProductService productService;

    ProductController(@Qualifier("fakeStoreService") ProductService productService){
        this.productService = productService;
    }

    @PostMapping("")
    public CreateProductResponse createProduct(@RequestBody CreateProductRequest CreateProductData ){
        Product product =  productService.createProduct(CreateProductData.toProduct());

        return CreateProductResponse.fromProduct(product);
    }

    @GetMapping("")
    public GetAllProductsResponse getAllProducts(){
        ArrayList<Product> products = productService.getAllProducts();

        return GetAllProductsResponse.fromProducts(products);
    }

    @GetMapping("{id}")
    public String getSingleProduct(@PathVariable("id")Long id){
        return "Hey!! this is your id : " + id;
    }

    @DeleteMapping("{id}")
    public void deleteProduct(@PathVariable("id") Long id){

    }

    //custom mapping
    @RequestMapping(name ="GANESH" , value = "")
    public String getMeEverything(){
      return "Hello!! You are calling custom http request method";
    }
}
