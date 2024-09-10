package com.scaler.productservice.services;

import com.scaler.productservice.dtos.search.FilterDto;
import com.scaler.productservice.dtos.search.SortingCriteria;
import com.scaler.productservice.models.Category;
import com.scaler.productservice.models.Product;
import com.scaler.productservice.repositories.CategoryRepository;
import com.scaler.productservice.repositories.ProductRepository;
import com.scaler.productservice.services.filteringService.FilterFactory;
import com.scaler.productservice.services.sortingService.SorterFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {
    private ProductRepository productRepository;

    public SearchService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> search(String query, List<FilterDto> filters, SortingCriteria sortingCriteria, Integer page, Integer size) {
        List<Product> products = productRepository.findByTitleContaining(query);

        // Apply filters
        for(FilterDto filter: filters) {
            products = FilterFactory.getFilterFromKey(filter.getKey()).apply(products, filter.getValues());
        }

        // Apply sorting
        products = SorterFactory.getSorterByCriteria(sortingCriteria).sort(products);

        List<Product> productsOnPage = new ArrayList<>();

        for(int i= (page-1)*size; i<=(page*size -1) && i<products.size(); ++i) {
            productsOnPage.add(products.get(i));
        }

        //return new PageImpl<>(products);
        Pageable pageable = PageRequest.of(page, size);
        return new PageImpl<>(productsOnPage, pageable, products.size());

    }

    public Page<Product> simpleSearch(String query, Long categoryId, String sortingAttribute, int page, int size) {
        Page<Product> products = productRepository
                .findAllByTitleContainingAndCategory_Id(
                        query, categoryId,
                        PageRequest.of(
                                page,
                                size,
                                Sort.by(sortingAttribute).ascending()));

//        Page<Product> products = productRepository
//                .findAllByTitleContainingAndCategory_Id(
//                        query, categoryId,
//                        PageRequest.of(
//                                page,
//                                size,
//                                Sort.by(sortingAttribute).descending()));
       return products;
    }
}
