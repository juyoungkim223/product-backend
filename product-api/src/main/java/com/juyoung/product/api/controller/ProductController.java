package com.juyoung.product.api.controller;

import com.juyoung.product.api.service.ProductService;
import com.juyoung.product.data.dto.api.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Product API", description = "API for managing products")
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    @Operation(summary = "1. 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API",
            description = "각 카테고리에서 가격이 가장 저렴한 상품의 브랜드와 가격을 조회하고, 총액을 계산합니다.")
    @GetMapping("/cheapest")
    public ResponseEntity<CheapestProductResponseDTO> getCheapestByCategory() {
        return ResponseEntity.ok(productService.getCheapestByCategory());
    }

    @Operation(summary = "2. 단일 브랜드로 모든 카테고리 상품 구매 시 최저가격 조회",
            description = "단일 브랜드의 카테고리 별 상품 가격과 총합을 조회합니다.")
    @GetMapping("/brand")
    public ResponseEntity<BrandWithTotalPriceResponseDTO> getBrandTotalPrice() {
        BrandWithTotalPriceResponseDTO response = productService.getCheapestBrandWithTotalPrice();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "3. 카테고리 이름으로 최저, 최고 가격 조회",
            description = "특정 카테고리에서 최저가 상품과 최고가 상품을 조회합니다.")
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<CategoryPriceRangeResponseDTO> getCategoryPriceRange(@PathVariable String categoryName) {
        CategoryPriceRangeResponseDTO response = productService.getCategoryPriceRange(categoryName);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "4. 상품 추가 API",
            description = "상품 정보를 추가합니다.")
    @PostMapping
    public ResponseEntity<String> addProduct(@Valid @RequestBody ProductRequestDTO requestDTO) {
        productService.addProduct(requestDTO);
        return ResponseEntity.ok("Product added successfully!");
    }

    @Operation(summary = "4. 상품 업데이트 API",
            description = "상품 정보를 업데이트합니다.")
    @PutMapping
    public ResponseEntity<String> UpdateProduct(@Valid @RequestBody ProductUpdateRequestDTO requestDTO) {
        productService.updateProduct(requestDTO);
        return ResponseEntity.ok("Product updated successfully!");
    }

    @Operation(summary = "4. 상품 삭제 API",
            description = "상품 ID를 기준으로 데이터를 삭제합니다.")
    @DeleteMapping
    public ResponseEntity<String> deleteProduct(@Valid @RequestBody ProductDeletionRequestDTO requestDTO) {
        productService.deleteProduct(requestDTO);
        return ResponseEntity.ok("Product deleted successfully!");
    }
}
