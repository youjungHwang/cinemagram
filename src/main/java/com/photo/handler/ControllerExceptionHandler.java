package com.photo.handler;

import com.photo.handler.exception.CustomValidationApiException;
import com.photo.handler.exception.CustomValidationException;
import com.photo.util.Popup;
import com.photo.web.dto.ResDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;


@RestController
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e){
        return Popup.historyBack(e.getErrors().toString());
    }

    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<?> validationApiException(CustomValidationApiException e){
        return new ResponseEntity<>(new ResDto<>(-1,e.getMessage(),e.getErrors()), HttpStatus.BAD_REQUEST);
    }
}
