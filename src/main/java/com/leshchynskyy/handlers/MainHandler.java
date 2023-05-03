package com.leshchynskyy.handler;

import com.leshchynskyy.entity.Request;
import com.leshchynskyy.enums.HttpMethod;
import com.leshchynskyy.io.RequestParser;
import com.leshchynskyy.io.ResponceWriter;
import com.leshchynskyy.util.ServerException;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.OutputStream;

public class MainHandler {

    @SneakyThrows
    public void handle(BufferedReader reader, OutputStream outputStream, String sourcePath) {
        ServerException.validateResourcePath(sourcePath);
        RequestParser parser = new RequestParser();
        Request request = parser.parse(reader);
        if (!request.getMethod().equals(HttpMethod.GET)) {
            throw ServerException.methodNotAllowed(outputStream);
        }

        if (request.getUri().length() == 0 || request.getHeaders().isEmpty()) {
            throw ServerException.badRequest(outputStream);
        }

        ResponceWriter responceWriter = new ResponceWriter();
        responceWriter.responce(request, outputStream, sourcePath);
    }
}
