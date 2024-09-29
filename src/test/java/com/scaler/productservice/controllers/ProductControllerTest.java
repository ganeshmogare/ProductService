package com.scaler.productservice.controllers;

import com.scaler.productservice.dtos.GetProductResponse;
import com.scaler.productservice.models.Category;
import com.scaler.productservice.models.Product;
import com.scaler.productservice.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @MockBean(name = "dbService")
    private ProductService productService;

    @Test
    public void testGetProductById_WhenValidIdISPassed_ReturnsProductsSuccessfully(){
        //Arrange
        Product product = new Product();
        product.setTitle("Mi A1");
        product.setDescription("Best MI Phone");
        product.setPrice(14000.00);
        Category category = new Category();
        category.setName("Mobile phones");
        product.setCategory(category);
        product.setId(1L);
        when(productService.getProduct(1L)).thenReturn(product);

        //Act
        GetProductResponse response = productController.getSingleProduct(1L);

        //Assertions
        assertNotNull(response);
        assertEquals("Mi A1", response.getTitle());
        assertEquals("Best MI Phone", response.getDescription());
        assertEquals(14000.00, response.getPrice());
        assertEquals("Mobile phones", response.getCategory());
        assertEquals(1L, response.getId());
    }

    @Test
    public void testGetProductById_WhenInvalidIdIsPassed_ResultsInRunTimeException(){

        RuntimeException ex = assertThrows(RuntimeException.class,
                () ->  productController.getSingleProduct(0L));
        assertEquals("Something very bad",ex.getMessage());
//        assertThrows( RuntimeException.class ,
//                ()->productController.getSingleProduct(0L)
//        );
    }
}
