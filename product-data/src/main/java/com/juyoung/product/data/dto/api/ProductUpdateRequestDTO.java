package com.juyoung.product.data.dto.api;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductUpdateRequestDTO {
    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Brand ID is required")
    private Long brandId;

    @NotNull(message = "Price is required")
    private Integer price;
}
