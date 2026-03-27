package com.springboot.advice;

import com.springboot.exception.BusinessLogicException;
import com.springboot.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        final ErrorResponse response = ErrorResponse.of(e.getBindingResult());

        return response;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(
            ConstraintViolationException e) {
        final ErrorResponse response = ErrorResponse.of(e.getConstraintViolations());

        return response;
    }

    @ExceptionHandler
    public ResponseEntity handleBusinessLogicException(BusinessLogicException e) {
            // ErrorResponse객체를 response에다가 넣기
            final ErrorResponse response = ErrorResponse.of(e.getExceptionCode());

        // TODO GlobalExceptionAdvice 기능 추가 1
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    // TODO GlobalExceptionAdvice 기능 추가 2
    @ExceptionHandler
    public ResponseEntity  handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException  e) {
        final ErrorResponse response = ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);   // 405에러(HTTP 메서드를 잘못 씀)
    }

    // TODO GlobalExceptionAdvice 기능 추가 3
    @ExceptionHandler
    public ResponseEntity handleException(Exception e) {
        final ErrorResponse response = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);    // 500에러(서버에서 문제가 발생함 > 개발자 실수)
    }
}
