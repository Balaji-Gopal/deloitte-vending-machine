package com.deloitte.base.exception;

public class OutOfStockException extends RuntimeException {
    private String message;

    public OutOfStockException(String string) {
        this.message = string;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
