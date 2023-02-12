package com.photo.handler.aop;

import com.photo.handler.exception.CustomValidationApiException;
import com.photo.handler.exception.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class ValidationAdvice {

    @Around("execution(* com.photo.web.api.*Controller.*(..))")
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] params = proceedingJoinPoint.getArgs();
        for(Object param : params) {
            if(param instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) param;

                if(bindingResult.hasErrors()) {
                    Map<String, String> errors = new HashMap<>();
                    for (FieldError error : bindingResult.getFieldErrors()) {
                        errors.put(error.getField(), error.getDefaultMessage());
                    }
                    throw new CustomValidationApiException("유효성 검사에 실패하였습니다.", errors);
                }
            }
        }
        return proceedingJoinPoint.proceed();
    }

    @Around("execution(* com.photo.web.*Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] params = proceedingJoinPoint.getArgs();
        for(Object param : params) {
            if(param instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) param;

                if(bindingResult.hasErrors()) {
                    Map<String,String> errors = new HashMap<>();

                    for(FieldError error : bindingResult.getFieldErrors()) {
                        errors.put(error.getField(),error.getDefaultMessage());
                    }
                    throw new CustomValidationException("유효성 검사에 실패하였습니다.", errors);
                }
            }
        }
        return proceedingJoinPoint.proceed();
    }
}
