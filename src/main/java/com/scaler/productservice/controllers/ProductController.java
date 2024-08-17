package com.scaler.productservice.controllers;

import com.scaler.productservice.dtos.*;
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

    @GetMapping("/{id}")
    public GetProductResponse getSingleProduct(@PathVariable("id")Long id){
        Product product = productService.getProduct(id);

        return GetProductResponse.fromProduct(product);
    }

    @DeleteMapping("{id}")
    public String deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);

        return "Product with id "+ id + " is deleted successfully!!!";
    }

    @PatchMapping("{id}")
    public UpdateProductResponse updateProduct(@RequestBody UpdateProductRequest updateProductData, @PathVariable("id") Long id){
        Product product = productService.updateProduct(id, updateProductData.toProduct());
        return UpdateProductResponse.fromProduct(product);
    }


    @PutMapping("{id}")
    public String replaceProduct(@RequestBody ReplaceProductRequest replaceProductData, @PathVariable("id") Long id){
        return productService.replaceProduct(id, replaceProductData.toProduct());
        //return ReplaceProductResponse.fromProduct(product);
    }

    //custom mapping
    @RequestMapping(name ="GANESH" , value = "")
    public String getMeEverything(){
      return "Hello!! You are calling custom http request method";
    }
}
