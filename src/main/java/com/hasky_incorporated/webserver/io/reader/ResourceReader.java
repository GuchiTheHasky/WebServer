package com.hasky_incorporated.webserver.io.reader;

import lombok.SneakyThrows;

import java.io.BufferedReader;

public class ResourceReader {

    @SneakyThrows
    public String getContent(BufferedReader reader) {
        StringBuilder builder = new StringBuilder();
        String content = "";
        while ((content = reader.readLine()) != null) {
            builder.append(content).append("\r\n");
            if (content.equals("")) {
                break;
            }
        }
        return builder.toString();
    }
}
