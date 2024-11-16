package com.juyoung.product.data.dto.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryPriceRangeResponseDTO {
    private String category;
    private ProductResponseDTO cheapest;
    private ProductResponseDTO mostExpensive;
}
