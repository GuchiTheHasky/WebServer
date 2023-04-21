package com.hasky_incorporated.webserver.handlers;

import com.hasky_incorporated.webserver.etities.Request;
import com.hasky_incorporated.webserver.io.parser.RequestParser;


import java.io.BufferedReader;
import java.io.BufferedWriter;

public class MainHandler {

    public void handle(BufferedReader reader, BufferedWriter writer, String sourcePath) {
        RequestParser parser = new RequestParser();
        Request request = parser.parse(reader);
        System.out.println(request);


    }


}
