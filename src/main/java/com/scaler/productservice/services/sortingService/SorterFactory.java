package com.scaler.productservice.services.sortingService;

import com.scaler.productservice.dtos.search.SortingCriteria;

public class SorterFactory {
    public static Sorter getSorterByCriteria(SortingCriteria criteria) {
        return switch (criteria){
            case PRICE_LOW_TO_HIGH -> new PriceLowToHighSorter();
            case PRICE_HIGH_TO_LOW -> new PriceHightToLowSorter();
            case RELEVANCE -> new RelevanceSorter();
            case POPULARITY -> new PopularitySorter();
            case RATING_LOW_TO_HIGH -> new RatingHighToLow();
            case RATING_HIGH_TO_LOW -> new RatingHighToLow();
            case null, default -> throw new IllegalStateException("Unexpected value: " + criteria);
        };
    }
}
