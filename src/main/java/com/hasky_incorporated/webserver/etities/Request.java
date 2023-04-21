package com.hasky_incorporated.webserver.etities;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Request {
    private String method;
    private String uri;
    private String version;
    private Map<String, String> headers;
}
