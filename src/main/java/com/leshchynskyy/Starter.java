package com.leshchynskyy;

import com.leshchynskyy.server.Server;
import com.leshchynskyy.util.ServerException;

import java.io.IOException;
import java.net.ServerSocket;

public class Starter {
    public static void isPortAvailable(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
        } catch (IOException e) {
            throw new ServerException("Port already in used.");
        }
    }

    public static void main(String[] args) {
        int port = 1024;
        String sourcePath = "src\\test\\resources\\webApp";
        Server server = new Server(port, sourcePath);
        isPortAvailable(port);
        server.start();
    }

}

