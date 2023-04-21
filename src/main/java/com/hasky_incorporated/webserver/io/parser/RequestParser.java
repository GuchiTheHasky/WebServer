package com.hasky_incorporated.webserver.io.parser;

import com.hasky_incorporated.webserver.etities.HttpMethod;
import com.hasky_incorporated.webserver.etities.Request;
import com.hasky_incorporated.webserver.io.reader.ResourceReader;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {

    public Request parse(BufferedReader reader) {
        String content = requestContent(reader);
        String[] title = getHttpRequestTitle(content);
        String method = getHttpMethod(title);
        String uri = getUri(title);
        String version = getProtocolVersion(title);
        Map<String, String> headers = getHeaders(content);
        return new Request(method, uri, version, headers);
    }

    private String requestContent(BufferedReader reader) {
        ResourceReader resourceReader = new ResourceReader();
        return resourceReader.getContent(reader);
    }

    private String[] getHttpRequestTitle(String content) {
        String[] lines = content.split("\r\n");
        return lines[0].split(" ");
    }

    private String getHttpMethod(String... title) {
        return HttpMethod.seekMethod(title);
    }

    private String getUri(String... title) {
        return title[1];
    }

    private String getProtocolVersion(String... title) {
        return title[2];
    }

    private HashMap<String, String> getHeaders(String content) {
        HashMap<String, String> headers = new HashMap<>();
        String[] lines = content.split("\r\n"); //"\\r?\\n
        for (int i = 1; i < lines.length; i++) {
            String[] parts = lines[i].split(":", 2);
            if (parts.length == 2) {
                String key = parts[0];
                String value = parts[1];
                headers.put(key, value);
            }
        }
        return headers;
    }
}
