package com.leshchynskyy;

import com.leshchynskyy.server.Server;

import java.io.IOException;

public class Starter {

    public static void main(String[] args) throws IOException {
        int port = 1024;
        String sourcePath = "src\\test\\resources\\webapp";
        Server server = new Server(port, sourcePath);
        server.start();
    }
}

