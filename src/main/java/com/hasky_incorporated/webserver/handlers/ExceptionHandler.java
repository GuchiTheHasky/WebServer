package com.hasky_incorporated.webserver.handlers;

public class ExceptionHandler extends RuntimeException {
    public ExceptionHandler(String message) {
        super(message);
    }
}
