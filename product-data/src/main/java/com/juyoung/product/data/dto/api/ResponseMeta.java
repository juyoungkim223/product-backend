package com.juyoung.product.data.dto.api;

import lombok.Getter;

@Getter
public enum ResponseMeta {
    PRODUCT_NOT_FOUND("PRODUCT_NOT_FOUND", "No product found"),
    CATEGORY_NOT_FOUND("CATEGORY_NOT_FOUND", "Category not found with the given ID"),
    BRAND_NOT_FOUND("BRAND_NOT_FOUND", "Brand not found with the given ID"),
    INVALID_INPUT("INVALID_INPUT", "Invalid input provided"),
    PRODUCT_ALREADY_EXISTS("PRODUCT_ALREADY_EXISTS", "A product already exists for the specified category and brand"),
    GENERAL_ERROR("GENERAL_ERROR", "An unexpected error occurred");


    private final String code;
    private final String message;

    ResponseMeta(String code, String message) {
        this.code = code;
        this.message = message;
    }

}