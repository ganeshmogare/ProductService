package com.scaler.productservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaler.productservice.configs.ApplicationConfiguration;
import com.scaler.productservice.configs.RedisConfig;
import com.scaler.productservice.dtos.CreateProductRequest;
import com.scaler.productservice.dtos.CreateProductResponse;
import com.scaler.productservice.dtos.GetAllProductsResponse;
import com.scaler.productservice.dtos.GetProductResponse;
import com.scaler.productservice.models.Category;
import com.scaler.productservice.models.Product;
import com.scaler.productservice.services.ProductService;
import jakarta.ws.rs.core.MediaType;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@Import({ApplicationConfiguration.class, RedisConfig.class})
public class ProductControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean(name = "dbService")
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;

    //@Autowired
   // private RestTemplate restTemplate;

    @Test
    public void TestGetAllProducts_Positive() throws Exception {
        Product product = new Product();
        product.setTitle("iPhone 16");
        product.setDescription("Newly launched iPhone");
        product.setPrice(100000.00);
        Category category = new Category();
        category.setName("Mobile phones");
        product.setCategory(category);
        product.setId(5L);

        GetAllProductsResponse productsList = new GetAllProductsResponse();
        ArrayList<Product> products = new ArrayList<>();
        products.add(product);

        ArrayList<GetProductResponse> responseProducts = new ArrayList<>();
        responseProducts.add(GetProductResponse.fromProduct(product));
        productsList.setProducts(responseProducts);

        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/products/"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productsList)));
               // .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$.title").value( "iPhone 16"))
//                .andExpect(jsonPath("$.description").value("Newly launched iPhone"))
//                .andExpect(jsonPath("$.price").value(100000.00))
//                .andExpect(jsonPath("$.category").value("Mobile phones"))
//                .andExpect(jsonPath("$.id").value(6L));
//                .andExpect(jsonPath("$[1].title", is("Mi A2")))
//                .andExpect(jsonPath("$[1].description", is("Best MI Phone")))
//                .andExpect(jsonPath("$[1].price", is(15000.00)))
//                .andExpect(jsonPath("$[1].category", is("Mobile phones")))
//                .andExpect(jsonPath("$[1].id", is(2)));
    }


    @Test
    public void TestCreateProduct_RunsSuccessfully() throws Exception {
        CreateProductRequest productDto = new CreateProductRequest();
        productDto.setTitle("MacBook Pro");
        //productDto.setPrice(100000.00);
        productDto.setDescription("Newly launched MacBook Pro");
        //productDto.setImageUrl("https://www.apple.com/macbook-pro-13/");
       // productDto.setCategory("Laptops");

        CreateProductResponse product = new CreateProductResponse();
        product.setTitle("MacBook Pro");
        product.setId(null);
        //product.setPrice(100000.00);
        product.setDescription("Newly launched MacBook Pro");
       // product.setImageUrl("https://www.apple.com/macbook-pro-13/");
       // product.setCategory("Laptops");

        when(productService.createProduct(any(Product.class))).thenReturn(productDto.toProduct());


        mockMvc.perform(post("/products/test_create")
                        .content(objectMapper.writeValueAsString(product)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productDto)))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.title").value("MacBook Pro"));
    }
}
