package com.scaler.productservice.services.filteringService;

public class FilterFactory {
    public static Filter getFilterFromKey(String key) {
//        if (key == null) {
//            return null;
//        }

        return switch(key) {
            case "ram" -> new RamFilter();
            case "brand" -> new BrandFilter();
            case null, default ->  null;
        };
    }
}
