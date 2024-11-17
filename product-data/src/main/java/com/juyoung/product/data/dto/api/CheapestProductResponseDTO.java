package com.juyoung.product.data.dto.api;

import com.juyoung.product.data.util.PriceFormatterUtil;
import lombok.Data;
import java.util.List;

@Data
public class CheapestProductResponseDTO {
    private List<ProductResponseDTO> products;
    private String totalAmount;
    public CheapestProductResponseDTO(List<ProductResponseDTO> products, int totalAmount) {
        this.products = products;
        this.totalAmount = PriceFormatterUtil.formatPrice(totalAmount);
    }
}
