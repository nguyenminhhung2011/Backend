package com.FitnessApp.Exceptions;

import com.FitnessApp.DTO.DataClass.ResponseObject;
import com.FitnessApp.Exceptions.AppException.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestControllerAdvice
public class AppExceptionHandler {


    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ResponseObject> handleException(Exception e) {
        ResponseObject errorResponse = new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        log.warn("Exception: {}", e.getMessage());
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseObject> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, Object> mapError = new HashMap<>();
        e.getFieldErrors().forEach(error -> mapError.put(error.getField(), error.getDefaultMessage()));
        ResponseObject errorResponse = new ResponseObject(HttpStatus.BAD_REQUEST.value(), "Error validation", mapError);
        log.warn("MethodArgumentNotValidException: {}", e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ResponseObject> handleIllegalArgumentException(IllegalArgumentException e) {
        ResponseObject errorResponse = new ResponseObject(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        log.warn("IllegalArgumentException: {}", e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ResponseObject> handleBadRequestException(BadRequestException e) {
        ResponseObject errorResponse = new ResponseObject(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        log.warn("BadRequestException: {}", e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ResponseObject> handleNotFoundException(NotFoundException e) {
        ResponseObject errorResponse = new ResponseObject(HttpStatus.NOT_FOUND.value(), e.getMessage(), null);
        log.warn("NotFoundException: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(value = ConflictException.class)
    public ResponseEntity<ResponseObject> handleConflictException(ConflictException e) {
        ResponseObject errorResponse = new ResponseObject(HttpStatus.CONFLICT.value(), e.getMessage(), null);
        log.warn("ConflictException: {}", e.getMessage());
        return ResponseEntity.status(errorResponse.status()).body(errorResponse);
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ResponseEntity<ResponseObject> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        ResponseObject errorResponse = new ResponseObject(HttpStatus.PAYLOAD_TOO_LARGE.value(), e.getMessage(), null);
        log.warn("MaxUploadSizeExceededException: {}", e.getMessage());
        return ResponseEntity.status(errorResponse.status()).body(errorResponse);
    }

    @ExceptionHandler(value = NotProcessException.class)
    public ResponseEntity<ResponseObject> handleNotProcessException(NotProcessException e) {
        ResponseObject errorResponse = new ResponseObject(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage(), null);
        log.warn("NotProcessException: {}", e.getMessage());
        return ResponseEntity.status(errorResponse.status()).body(errorResponse);
    }

    @ExceptionHandler(value = MessagingException.class)
    public ResponseEntity<ResponseObject> handleMessagingException(MessagingException e) {
        ResponseObject errorResponse = new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        log.warn("MessagingException: {}", e.getMessage());
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    @ExceptionHandler(value = TemplateInputException.class)
    public ResponseEntity<ResponseObject> handleTemplateInputException(TemplateInputException e) {
        ResponseObject errorResponse = new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        log.warn("TemplateInputException: {}", e.getMessage());
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<ResponseObject> handleAuthenticationException(AuthenticationException e) {
        ResponseObject errorResponse = new ResponseObject(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
        log.warn("AuthenticationException: {}", e.getMessage());
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<ResponseObject> handleBadCredentialsException(BadCredentialsException e) {
        ResponseObject errorResponse = new ResponseObject(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        log.warn("BadCredentialsException: {}", e.getMessage());
        return ResponseEntity.status(errorResponse.status()).body(errorResponse);
    }

}
