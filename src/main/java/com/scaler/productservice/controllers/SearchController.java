package com.scaler.productservice.controllers;

import com.scaler.productservice.dtos.GetProductResponse;
import com.scaler.productservice.dtos.search.FilterDto;
import com.scaler.productservice.dtos.search.SearchResponseDto;
import com.scaler.productservice.dtos.search.SortingCriteria;
import com.scaler.productservice.models.Product;
import com.scaler.productservice.services.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
public class SearchController {
    private SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }
    @GetMapping("/")
    public SearchResponseDto search(@RequestParam("query") String query , @RequestParam("filters") List<FilterDto> filters, @RequestParam("sortBy")SortingCriteria sortingCriteria,
                                    @RequestParam("page") int page, @RequestParam("size") int size) {
        SearchResponseDto response = new SearchResponseDto();
        Page<Product> productsPage = searchService.search(query, filters, sortingCriteria, page, size);
        Page<GetProductResponse> getProductResponsePage = convertToGetProductResponsePage(productsPage);
        response.setProductsPage(getProductResponsePage);
        return response;
    }

    public Page<GetProductResponse> convertToGetProductResponsePage(Page<Product> productPage) {
        List<GetProductResponse> getProductResponses = productPage.getContent().stream()
                .map(GetProductResponse::fromProduct)
                .collect(Collectors.toList());
        Pageable pageable = PageRequest.of(productPage.getNumber(), productPage.getSize(), productPage.getSort());
        Page<GetProductResponse> getProductPage = new PageImpl<>(getProductResponses, pageable, productPage.getTotalElements());

        return getProductPage;
    }

    @GetMapping("/searchByCategory")
    public SearchResponseDto simpleSearch(@RequestParam("query") String query , @RequestParam("categoryId") Long categoryId, @RequestParam("sortBy")String sortingAttribute,
                                    @RequestParam("page") int page, @RequestParam("size") int size) {

        return null;
    }
}
