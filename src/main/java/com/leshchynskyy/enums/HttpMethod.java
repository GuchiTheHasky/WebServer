package com.leshchynskyy.enums;

import com.leshchynskyy.util.ServerException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum HttpMethod {
    GET("GET"),
    POST("POST");

    private String essence;

    public static HttpMethod getMethodByEssence(String essence) {
        for (HttpMethod value : values()) {
            if (value.essence.equals(essence)) {
                return value;
            }
        }
        throw new ServerException("Cannot find HTTP method with essence: " + essence);
    }
}
