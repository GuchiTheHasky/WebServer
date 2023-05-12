package com.leshchynskyy.io;

import lombok.Cleanup;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestParserTest {
    private static final String CRLF = "\r\n";

    @Test
    @DisplayName("Test, get HttpRequest status line uri and protocol version.")
    public void testGetHttpRequestTitleURIAndProtocolVersion() {
        String requestContent = getContent("src\\test\\resources\\requestfortest");
        String[] expected = "GET /hello.htm HTTP/1.1".split(" ");
        String[] actual = RequestParser.getHttpRequestStatusLine(requestContent);
        assertEquals(Arrays.toString(expected), Arrays.toString(actual));

        String expectedUri = "/hello.htm";
        String actualUri = RequestParser.getUri(actual);
        assertEquals(expectedUri, actualUri);

        String expectedVersion = "HTTP/1.1";
        String actualVersion = RequestParser.getProtocolVersion(actual);
        assertEquals(expectedVersion, actualVersion);
    }

    @Test
    @DisplayName("Test, get headers from content.")
    public void testGetHeadersFromContent() {
        String requestContent = getContent("src\\test\\resources\\requestfortest");
        HashMap<String, String> actualHeaders = RequestParser.getHeaders(requestContent);
        String actualValue = actualHeaders.get("User-Agent");
        String actualValue1 = actualHeaders.get("Host");
        String actualValue2 = actualHeaders.get("Accept-Language");
        String actualValue3 = actualHeaders.get("Accept-Encoding");
        String actualValue4 = actualHeaders.get("Connection");

        assertEquals(" Mozilla/4.0 (compatible; MSIE5.01; Windows NT)", actualValue);
        assertEquals(" www.tutorialspoint.com", actualValue1);
        assertEquals(" en-us", actualValue2);
        assertEquals(" gzip, deflate", actualValue3);
        assertEquals(" Keep-Alive", actualValue4);
    }

    @SneakyThrows
    private String getContent(String path) {
        StringBuilder builder = new StringBuilder();
        @Cleanup BufferedReader reader = new BufferedReader(new FileReader(path));
        String line = "";
        while ((line = reader.readLine()) != null) {
            builder.append(line).append(CRLF);
        }
        return builder.toString();
    }
}
