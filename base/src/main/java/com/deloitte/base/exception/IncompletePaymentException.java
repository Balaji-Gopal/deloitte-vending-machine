package com.deloitte.base.exception;

public class IncompletePaymentException extends RuntimeException {

    private String message;
    private long balance;

    public IncompletePaymentException(String message, long balance) {
        this.message = message; this.balance = balance;
    }

    @Override
    public String getMessage(){
        return message + balance;
    }
}
