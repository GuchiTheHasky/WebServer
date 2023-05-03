package com.leshchynskyy.util;

import com.leshchynskyy.enums.HttpStatusCode;
import com.leshchynskyy.io.ResourceReader;
import lombok.SneakyThrows;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ServerException extends RuntimeException {
    private static final String CRLF = "" + (char) 0x0D + (char) 0x0A;

    public ServerException(String message) {
        super(message);
    }

    @SneakyThrows
    public static ServerException badRequest(OutputStream outputStream) {
        outputStream.write("HTTP/1.1".getBytes());
        outputStream.write(HttpStatusCode.BAD_REQUEST.getStatus().getBytes());
        headers(outputStream);
        ResourceReader resourceReader = new ResourceReader();
        String path = "src\\main\\resources\\errorpage\\400.html";
        resourceReader.outputStream(outputStream, path);
        throw new ServerException("Bad request.");
    }

    @SneakyThrows
    public static ServerException fileNotFound(OutputStream outputStream) {
        outputStream.write("HTTP/1.1 ".getBytes());
        outputStream.write((HttpStatusCode.NOT_FOUND.getCode() +
                HttpStatusCode.NOT_FOUND.getStatus()).getBytes());
        outputStream.write(CRLF.getBytes());
        headers(outputStream);
        ResourceReader resourceReader = new ResourceReader();
        String path = "src\\main\\resources\\errorpage\\404.html";
        resourceReader.outputStream(outputStream, path);
        throw new ServerException("File not found.");
    }

    @SneakyThrows
    public static ServerException methodNotAllowed(OutputStream outputStream) {
        outputStream.write("HTTP/1.1 ".getBytes());
        outputStream.write((HttpStatusCode.METHOD_NOT_ALLOWED.getCode() +
                HttpStatusCode.METHOD_NOT_ALLOWED.getStatus()).getBytes());
        headers(outputStream);
        ResourceReader resourceReader = new ResourceReader();
        String path = "src\\main\\resources\\errorpage\\405.html";
        resourceReader.outputStream(outputStream, path);
        throw new ServerException("Method not allowed.");
    }

    @SneakyThrows
    public static ServerException internalServerError(OutputStream outputStream) {
        outputStream.write("HTTP/1.1 ".getBytes());
        outputStream.write((HttpStatusCode.INTERNAL_SERVER_ERROR.getCode() +
                HttpStatusCode.INTERNAL_SERVER_ERROR.getStatus()).getBytes());
        headers(outputStream);
        ResourceReader resourceReader = new ResourceReader();
        String path = "src\\main\\resources\\errorpage\\500.html";
        resourceReader.outputStream(outputStream, path);
        throw new ServerException("Internal server error.");
    }

    @SneakyThrows
    private static void headers(OutputStream outputStream) {
        outputStream.write("Content-Type: text/html".getBytes());
        outputStream.write(CRLF.getBytes());
        outputStream.write(CRLF.getBytes());
    }

    public static void validateResourcePath(String resourcePath) {
        Path path = Paths.get(resourcePath);
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            throw new ServerException("Current path or directory " + resourcePath + " doesn't exist.");
        }
    }
}

