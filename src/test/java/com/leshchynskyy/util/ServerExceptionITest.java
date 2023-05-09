package com.leshchynskyy.util;

import com.leshchynskyy.server.Server;
import lombok.SneakyThrows;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ServerExceptionITest  {

    @SneakyThrows
    @Test
    @DisplayName("Test, connect with server try to get exception 404 file not found.")
    public void testConnectWithServerTryToGetException404FileNotFound() {
        Thread thread = new Thread(() -> {
            try {
                new Server(9995, "src\\test\\resources\\webApp").start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();

        CloseableHttpClient CLIENT = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:9995/notexist.html");
        CloseableHttpResponse response = CLIENT.execute(httpGet);

        String expectedTitle = "HTTP/1.1 404 Not Found";
        int titleLength = expectedTitle.length();
        String actualTitle = String.valueOf(response.getStatusLine()).substring(0, titleLength);
        assertEquals(expectedTitle, actualTitle);

        String expectedHeader = "Content-Type: text/html";
        String actualHeader = String.valueOf(response.getStatusLine()).substring(titleLength);
        assertEquals(expectedHeader, actualHeader);
    }
}
