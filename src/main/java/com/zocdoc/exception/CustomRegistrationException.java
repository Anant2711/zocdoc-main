package com.zocdoc.exception;

public class CustomRegistrationException extends RuntimeException {
    public CustomRegistrationException(String message){
        super(message);
    }
}
