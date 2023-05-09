package com.leshchynskyy.server;

import lombok.SneakyThrows;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ServerITest {

    @Test
    @SneakyThrows
    @DisplayName("Test, connect to local port check title & Content-Type.")
    public void testConnectToLocalPortCheckHttpRequest() {
        Thread thread = new Thread(() -> new Server(1026, "src\\test\\resources\\webApp").start());
        thread.start();

        CloseableHttpClient myClient = HttpClients.createDefault();
        CloseableHttpResponse response = myClient.execute(new HttpGet("http://localhost:1026/"));

        String expectedTitle = "HTTP/1.1 200 OK";
        String actualTitle = String.valueOf(response.getStatusLine());
        assertEquals(expectedTitle, actualTitle);

        String expectedHeader = "[Content-Type: text/html]";
        String actualHeader = Arrays.toString(response.getAllHeaders());
        assertEquals(expectedHeader, actualHeader);
    }
}



