package com.fitlife.app.Exceptions.AppException;

public class BadRequestException extends Throwable {
    public BadRequestException(String message) {
        super(message);
    }
}
