package com.banking.Sweep.Exception;

public class DoesNotExistException extends RuntimeException{
    public DoesNotExistException(String message){
        super(message);
    }
}
