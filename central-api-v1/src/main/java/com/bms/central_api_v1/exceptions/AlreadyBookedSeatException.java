package com.bms.central_api_v1.exceptions;

public class AlreadyBookedSeatException extends RuntimeException{
    public AlreadyBookedSeatException(String message){
        super(message);
    }
}
