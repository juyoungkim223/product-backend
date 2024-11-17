package com.juyoung.product.data.dto.api;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponseDTO {
    private String code;
    private String message;
    private LocalDateTime timestamp;
}

