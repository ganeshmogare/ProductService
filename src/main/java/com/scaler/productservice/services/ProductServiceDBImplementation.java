package com.scaler.productservice.services;

import com.scaler.productservice.dtos.CreateFakestoreProductRequest;
import com.scaler.productservice.dtos.CreateFakestoreProductResponse;
import com.scaler.productservice.exceptions.ProductNotFound;
import com.scaler.productservice.models.Category;
import com.scaler.productservice.models.Product;
import com.scaler.productservice.repositories.CategoryRepository;
import com.scaler.productservice.repositories.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("dbService")
//@Primary
public class ProductServiceDBImplementation implements ProductService{

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    private RedisTemplate redisTemplate;

    public ProductServiceDBImplementation(ProductRepository productRepository,
                                CategoryRepository categoryRepository, RedisTemplate redisTemplate) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.redisTemplate = redisTemplate;
    }
    @Override
    public Product createProduct(Product product) {
        Category toBePutInProduct = getCategoryToBeInProduct(product);

        product.setCategory(toBePutInProduct);
        product.setLast_updated_at(java.time.LocalDateTime.now());
        product.setCreated_at(java.time.LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        return savedProduct;
    }

    private Category getCategoryToBeInProduct(Product product) {
        String categoryName = product.getCategory().getName();

        Optional<Category> category =
                categoryRepository.findByName(categoryName);
        Category toBePutInProduct = null;

        if (category.isEmpty()) {
            Category toSaveCategory = new Category();
            toSaveCategory.setName(categoryName);

            toBePutInProduct = toSaveCategory;
            toBePutInProduct.setCreated_at(java.time.LocalDateTime.now());
        } else {
            toBePutInProduct = category.get();
        }
        toBePutInProduct.setLast_updated_at(java.time.LocalDateTime.now());
        return toBePutInProduct;
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

    @Override
    public ArrayList<Product> getAllProducts() {
        List<Product> products= productRepository.findAll();

        return new ArrayList<>(products);
    }

    @Override
    public Product getProduct(Long id) {
        //check if the product exists in the redis cache
        Product product = (Product) redisTemplate.opsForHash().get("PRODUCTS", "PRODUCT_"+id);

        //Cache hit , return the product
        if(product != null){
            return product;
        }

        //cache miss, query from Database
        product = productRepository.findById(id).get();

        //store product into cache
        redisTemplate.opsForHash().put("PRODUCTS", "PRODUCT_"+id, product);

        return product;
    }

    @Override
    public String deleteProduct(Long id) throws ProductNotFound{
        Product prod = productRepository.findById(id).get();
        if(prod == null){
            throw new ProductNotFound();
        }

        prod.set_deleted(true);
        prod.setLast_updated_at(java.time.LocalDateTime.now());

        productRepository.delete(prod);

        return "Product with id "+ id + " is deleted successfully!!!";

    }

    @Override
    public Product updateProduct(Long id, Product product) throws ProductNotFound {
        Optional<Product> productToUpdateOptional = productRepository.findById(id);

        if (productToUpdateOptional.isEmpty()) {
            throw new ProductNotFound();
        }

        Product productToUpdate = productToUpdateOptional.get();

        if (product.getDescription() != null) {
            productToUpdate.setDescription(product.getDescription());
        }

        if (product.getPrice() != null) {
            productToUpdate.setPrice(product.getPrice());
        }

        if (product.getTitle() != null) {
            productToUpdate.setTitle(product.getTitle());
        }

        if (product.getCategory().getName() != null) {
            Category toBePutInProduct = getCategoryToBeInProduct(product);

            productToUpdate.setCategory(toBePutInProduct);
        }

        productToUpdate.setLast_updated_at(java.time.LocalDateTime.now());

        return productRepository.save(productToUpdate);
    }

    @Override
    public Product replaceProduct(Long id, Product product) throws ProductNotFound{
        Optional<Product> productToUpdateOptional = productRepository.findById(id);

        if (productToUpdateOptional.isEmpty()) {
            throw new ProductNotFound();
        }

        product.setId(id);

        Category toBePutInProduct = getCategoryToBeInProduct(product);

        product.setCategory(toBePutInProduct);
        product.setLast_updated_at(java.time.LocalDateTime.now());
        product.setCreated_at(java.time.LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        return savedProduct;
    }
}
