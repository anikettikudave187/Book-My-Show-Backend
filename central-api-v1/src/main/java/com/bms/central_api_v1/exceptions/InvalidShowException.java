package com.bms.central_api_v1.exceptions;

public class InvalidShowException extends RuntimeException{
    public InvalidShowException(String message){
        super(message);
    }
}
