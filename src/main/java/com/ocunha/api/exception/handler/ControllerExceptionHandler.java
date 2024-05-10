package com.ocunha.api.exception.handler;

import com.ocunha.spec.api.ErrorResource;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    List<ErrorResource> exceptionHandler(MethodArgumentTypeMismatchException exception) {
        return List.of(ErrorResource.builder()
                .field(exception.getPropertyName())
                .value(String.valueOf(exception.getValue()))
                .description(String.format("Must be %s", exception.getRequiredType()))
                .build());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    List<ErrorResource> exceptionHandler(ConstraintViolationException exception) {
        return exception.getConstraintViolations()
                .stream()
                .map(constraintViolation -> ErrorResource.builder()
                        .field(((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().getName())
                        .value(String.valueOf(constraintViolation.getInvalidValue()))
                        .description(constraintViolation.getMessage())
                        .build())
                .toList();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    List<ErrorResource> exceptionHandler(RuntimeException e) {
        return List.of(ErrorResource.builder()
                .description(e.getMessage())
                .build());
    }

}
