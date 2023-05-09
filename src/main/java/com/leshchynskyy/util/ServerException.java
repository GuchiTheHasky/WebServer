package com.leshchynskyy.util;

import com.leshchynskyy.enums.HttpStatusCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ServerException extends RuntimeException {
    private HttpStatusCode statusCode;

    public ServerException(HttpStatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public ServerException(String message) {
        super(message);
    }
}

