package com.juyoung.product.data.dto.api;

import com.juyoung.product.data.util.PriceFormatterUtil;
import lombok.Data;
import java.util.List;

@Data
public class BrandWithTotalPriceResponseDTO {
    private String brand;
    private List<ProductResponseDTO> products;
    private String totalPrice;

    public BrandWithTotalPriceResponseDTO(String brand, List<ProductResponseDTO> products, int totalPrice) {
        this.brand = brand;
        this.products = products;
        this.totalPrice = PriceFormatterUtil.formatPrice(totalPrice);
    }
}
