package com.fitlife.app.Exceptions.AppException;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String msg){
        super(msg);
    }
}
