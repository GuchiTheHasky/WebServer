package com.hasky_incorporated.webserver;

import com.hasky_incorporated.webserver.handlers.ValidationHandler;
import com.hasky_incorporated.webserver.server.Server;


public class Starter {
    public static void main(String[] args) {
        Server server = ValidationHandler.validateAttributes(1024, "src\\main\\resources\\webApp");
        server.start();
    }
}
