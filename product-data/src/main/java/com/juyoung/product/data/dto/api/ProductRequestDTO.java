package com.juyoung.product.data.dto.api;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import jakarta.annotation.Nonnull;

@Data
public class ProductRequestDTO {

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Brand ID is required")
    private Long brandId;

    @NotNull(message = "Price is required")
    private Integer price;
}
