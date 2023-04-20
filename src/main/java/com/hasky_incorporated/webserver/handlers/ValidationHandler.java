package com.hasky_incorporated.webserver.handlers;

import java.net.Socket;

public class ValidationHandler {
    public static void isAccepted(Socket socket) {
        if (socket.isConnected()) {
            System.out.println("Client: " + socket + " connected.");
        }
    }
}
