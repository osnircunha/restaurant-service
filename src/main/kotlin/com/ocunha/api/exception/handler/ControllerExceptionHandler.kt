package com.ocunha.api.exception.handler

import com.ocunha.spec.api.ErrorResource
import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import org.hibernate.validator.internal.engine.path.PathImpl
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@ControllerAdvice
class ControllerExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun exceptionHandler(exception: MethodArgumentTypeMismatchException): List<ErrorResource?>? {
        val error = ErrorResource()
        error.field(exception.propertyName)
        error.value(exception.value.toString())
        error.description(String.format("must be %s", exception.requiredType))

        return listOf(error)
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException::class)
    fun exceptionHandler(exception: ConstraintViolationException): List<ErrorResource>? {
        return exception.constraintViolations
            .map { constraintViolation: ConstraintViolation<*> ->
                val error = ErrorResource()
                error.field((constraintViolation.propertyPath as PathImpl).leafNode.name)
                error.value(constraintViolation.invalidValue.toString())
                error.description(constraintViolation.message)
            }
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException::class)
    fun exceptionHandler(e: RuntimeException): List<ErrorResource?>? {
        val error = ErrorResource()
        error.description(e.message)
        return listOf(error)
    }
}