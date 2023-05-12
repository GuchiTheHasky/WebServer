package com.leshchynskyy.enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum HttpStatusCode {
    OK(200, "OK"),
    NOT_FOUND(404, "Not Found"),
    BAD_REQUEST(400, "Bad Request"),
    METHOD_NOT_ALLOWED(405, "Method not allowed"),
    INTERNAL_SERVER_ERROR(500, "Internal server error");

    private int code;
    private String status;

    HttpStatusCode(int code, String status) {
        this.code = code;
        this.status = status;
    }
}
