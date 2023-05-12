package com.leshchynskyy.io;

import com.leshchynskyy.entity.Request;
import com.leshchynskyy.enums.HttpMethod;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {
    private static final String CRLF = "\r\n";

    public static Request parse(BufferedReader reader) {
        String content = requestContent(reader);
        String[] statusLine = getHttpRequestStatusLine(content);
        String method = statusLine[0];
        HttpMethod httpMethod = HttpMethod.getMethodByEssence(method);
        String uri = getUri(statusLine);
        String version = getProtocolVersion(statusLine);
        Map<String, String> headers = getHeaders(content);
        return new Request(httpMethod, uri, version, headers);
    }

    private static String requestContent(BufferedReader reader) {
        ResourceReader resourceReader = new ResourceReader();
        return resourceReader.getContent(reader);
    }

    static String[] getHttpRequestStatusLine(String content) {
        String[] lines = content.split(CRLF);
        return lines[0].split(" ");
    }

    static String getUri(String... statusLine) {
        return statusLine[1];
    }

    static String getProtocolVersion(String... statusLine) {
        return statusLine[2];
    }

    static HashMap<String, String> getHeaders(String content) {
        HashMap<String, String> headers = new HashMap<>();
        String[] lines = content.split(CRLF);
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
