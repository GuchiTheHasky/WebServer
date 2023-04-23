package com.hasky_incorporated.webserver.handlers;

import com.hasky_incorporated.webserver.io.reader.ResourceReader;
import com.hasky_incorporated.webserver.server.Server;

import java.net.Socket;

public class ValidationHandler {
    public static void isAccepted(Socket socket) {
        if (socket.isConnected()) {
            System.out.println("Client: " + socket + " connected.");
        }
    }

    public static Server validateAttributes(int port, String path) {
        int newPort = portValidator(port);
        String sourcePath = sourceValidator(path);
        return new Server(newPort, sourcePath);
    }

    private static int portValidator(int port) {
        if (port > 1023 && port < 65535) {
            return port;
        }
        throw new ExceptionHandler("Incorrect port, try between 1024 and 65535.");
    }

    private static String sourceValidator(String path) {
        ResourceReader.validateResource(path);
        return path;
    }
}
