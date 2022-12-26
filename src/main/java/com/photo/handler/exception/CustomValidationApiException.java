package com.photo.handler.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class CustomValidationApiException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String message;
    private Map<String, String> errors;

    public CustomValidationApiException(String message) {
        this.message = message;
    }
    public CustomValidationApiException(String message, Map<String, String> errors) {
        this.message = message;
        this.errors = errors;
    }
}
