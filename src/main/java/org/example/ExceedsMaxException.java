package org.example;

public class ExceedsMaxException extends Exception{
    public ExceedsMaxException(String errorMessage){
        super(errorMessage);
    }
}
