package com.hasky_incorporated.webserver.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum HttpStatus {
    OK(" 200 OK"),
    NOT_FOUND(" 404 NOT_FOUND"),
    BAD_REQUEST(" 400 BAD_REQUEST"),
    INTERNAL_SERVER_ERROR(" 500 INTERNAL_SERVER_ERROR");

    private String status;

}
