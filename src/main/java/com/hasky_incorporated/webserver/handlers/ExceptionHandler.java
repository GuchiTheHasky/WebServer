package com.hasky_incorporated.webserver.handlers;

public class ExceptionHandler extends RuntimeException {
    public ExceptionHandler(String message) {
        super(message);
    }

    public ExceptionHandler(Throwable cause) {
        super(cause);
    }

    public ExceptionHandler(String message, Throwable cause) {
        super(message, cause);
    }
}
