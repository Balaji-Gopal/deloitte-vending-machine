package com.deloitte.base.exception;

public class NoChangeException extends RuntimeException {
    private String message;

    public NoChangeException(String string) {
        this.message = string;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
