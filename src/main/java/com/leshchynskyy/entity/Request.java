package com.leshchynskyy.entity;

import com.leshchynskyy.enums.HttpMethod;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Request {
    private HttpMethod method;
    private String uri;
    private String version;
    private Map<String, String> headers;
}
