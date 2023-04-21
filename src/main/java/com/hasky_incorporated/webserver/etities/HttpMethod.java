package com.hasky_incorporated.webserver.etities;

import com.hasky_incorporated.webserver.handlers.ExceptionHandler;

public enum HttpMethod {
    GET("GET"),
    POST("POST");

    private final String method;

    HttpMethod(String method) {
        this.method = method;
    }
    public static String seekMethod(String... values) {
        for (String method : values) {
            if (method.equals(HttpMethod.GET.name()) || method.equals(HttpMethod.POST.name())) {
                return method;
            }
        }
        throw new ExceptionHandler("HTTP method not recognized.");
    }
}
