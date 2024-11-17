package com.juyoung.product.data.dto.api;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDeletionRequestDTO {
    @NotNull(message = "Product ID is required")
    private Long productId;
}
