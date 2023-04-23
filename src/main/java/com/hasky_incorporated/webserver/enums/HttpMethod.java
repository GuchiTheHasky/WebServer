package com.hasky_incorporated.webserver.enums;

import com.hasky_incorporated.webserver.handlers.ExceptionHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum HttpMethod {
    GET("GET"),
    POST("POST");

    private String method;

    public static String seekMethod(String... values) {
        for (String method : values) {
            if (method.equals(HttpMethod.GET.name()) || method.equals(HttpMethod.POST.name())) {
                return method;
            }
        }
        throw new ExceptionHandler("HTTP method not recognized.");
    }
}
