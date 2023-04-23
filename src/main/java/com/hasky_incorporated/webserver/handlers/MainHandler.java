package com.hasky_incorporated.webserver.handlers;

import com.hasky_incorporated.webserver.entity.Request;
import com.hasky_incorporated.webserver.io.parser.RequestParser;
import com.hasky_incorporated.webserver.io.writer.ResponceWriter;


import java.io.BufferedReader;
import java.io.OutputStream;

public class MainHandler {

    public void handle(BufferedReader reader, OutputStream outputStream, String sourcePath) {
        RequestParser parser = new RequestParser();
        Request request = parser.parse(reader);
        ResponceWriter responceWriter = new ResponceWriter();
        responceWriter.writeResponce(request, outputStream, sourcePath);
    }
}
