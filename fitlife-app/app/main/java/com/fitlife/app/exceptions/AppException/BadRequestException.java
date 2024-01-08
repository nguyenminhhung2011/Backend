package com.fitlife.app.exceptions.AppException;

public class BadRequestException extends Throwable {
    public BadRequestException(String message) {
        super(message);
    }
}
