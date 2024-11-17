package com.juyoung.product.data.exception;

import com.juyoung.product.data.dto.api.ResponseMeta;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

    private final HttpStatus status;
    private final ResponseMeta responseMeta;

    public ApiException(ResponseMeta responseMeta, HttpStatus status) {
        super(responseMeta.getMessage());
        this.responseMeta = responseMeta;
        this.status = status;
    }
}
