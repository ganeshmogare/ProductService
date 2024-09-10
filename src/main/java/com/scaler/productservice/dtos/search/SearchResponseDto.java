package com.scaler.productservice.dtos.search;

import com.scaler.productservice.dtos.GetProductResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class SearchResponseDto {
    private Page<GetProductResponse> productsPage;
}
