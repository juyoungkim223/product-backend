package com.juyoung.product.data.dto.api;

import com.juyoung.product.data.util.PriceFormatterUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class BrandWithTotalPriceResponseDTO {
    private String brand;
    private List<ProductResponseDTO> products;
    private int totalPrice;
}
