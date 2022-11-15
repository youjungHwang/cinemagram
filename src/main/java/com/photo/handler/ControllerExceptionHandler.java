package com.photo.handler;

import com.photo.handler.exception.CustomValidationException;
import com.photo.web.dto.ResDto;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public ResDto<?> validationException(CustomValidationException e){
        return new ResDto<Map<String,String>>(-1,e.getMessage(), e.getErrors());
    }
}
