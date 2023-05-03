package com.leshchynskyy.io;

import com.leshchynskyy.entity.Request;
import com.leshchynskyy.enums.HttpMethod;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {
    private static final String CRLF = "" + (char) 0x0D + (char) 0x0A;

    public Request parse(BufferedReader reader) {
        String content = requestContent(reader);
        String[] title = getHttpRequestTitle(content);
        String method = title[0];
        HttpMethod httpMethod = HttpMethod.getMethodByEssence(method);
        String uri = getUri(title);
        String version = getProtocolVersion(title);
        Map<String, String> headers = getHeaders(content);
        return new Request(httpMethod, uri, version, headers);
    }

    private String requestContent(BufferedReader reader) {
        ResourceReader resourceReader = new ResourceReader();
        return resourceReader.getContent(reader);
    }

    String[] getHttpRequestTitle(String content) {
        String[] lines = content.split(CRLF);
        return lines[0].split(" ");
    }

    String getUri(String... title) {
        return title[1];
    }

    String getProtocolVersion(String... title) {
        return title[2];
    }

    HashMap<String, String> getHeaders(String content) {
        HashMap<String, String> headers = new HashMap<>();
        String[] lines = content.split("\r\n");
        int headerLimit = 2;
        for (int i = 1; i < lines.length; i++) {
            String[] headersParts = lines[i].split(":", headerLimit);
            if (headersParts.length == headerLimit) {
                String key = headersParts[0];
                String value = headersParts[1];
                headers.put(key, value);
            }
        }
        return headers;
    }
}
