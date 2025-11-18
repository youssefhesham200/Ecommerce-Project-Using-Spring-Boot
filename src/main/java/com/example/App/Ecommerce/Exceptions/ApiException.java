package com.example.App.Ecommerce.Exceptions;

public class ApiException extends RuntimeException{
    private static String msg;

    public ApiException(String message) {
        super(message);
    }

    public ApiException() {
    }
}
