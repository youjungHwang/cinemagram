package com.photo.handler.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

public class CustomException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public CustomException(String message) {
        super(message);
    }

}
