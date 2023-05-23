package com.leshchynskyy.util;

import com.leshchynskyy.server.Server;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class ServerExceptionITest {

    private final CloseableHttpClient CLIENT = HttpClients.createDefault();

    @BeforeAll
    static void init() {
        Thread thread = new Thread(() -> {
            new Server(1026, "src\\test\\resources\\webApp").start();
        });
        thread.start();
    }

    @Test
    @SneakyThrows
    @DisplayName("Test, send invalid request get responce 400 bad request.")
    public void testSendInvalidRequestGetResponce400BadRequest() {
        String[] responce = CustomClient.sendInvalidRequestAndGetResponse(1026).split("\r\n");

        String expectedStatusLine = "HTTP/1.1 400 Bad Request";
        String actualStatusLine = responce[0];
        assertEquals(expectedStatusLine, actualStatusLine);

        StringBuilder responceBody = new StringBuilder();
        for (int i = 3; i < responce.length; i++) {
            responceBody.append(responce[i]).append("\n");
        }

        String expectedResponceBody = getBadRequest();
        String actualResponceBody = responceBody.toString();
        assertEquals(expectedResponceBody, actualResponceBody);
    }

    @Test
    @SneakyThrows
    @DisplayName("Test, connect with server try to get exception 404 file not found.")
    public void testConnectWithServerGetException404FileNotFound() {
        CloseableHttpResponse response = CLIENT.execute(new HttpGet("http://localhost:1026/notexist.html"));
        StatusLine statusLine = response.getStatusLine();
        HttpEntity entity = response.getEntity();

        String expectedStatusLine = "HTTP/1.1 404 Not Found";
        String actualStatusLine = statusLine.toString();
        assertEquals(expectedStatusLine, actualStatusLine);

        String expectedHeader = "Content-Type: text/html";
        String actualHeader = entity.getContentType().toString();
        assertEquals(expectedHeader, actualHeader);

        String expectedEntityContent = getFileNotFound().replaceAll("\n", "\r\n");
        String actualEntityContent = IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8);
        assertEquals(expectedEntityContent, actualEntityContent);
    }

    @Test
    @SneakyThrows
    @DisplayName("Test, connect with server Post exception 405 method not allowed.")
    public void testConnectWithServerPostException405MethodNotAllowed() {
        CloseableHttpResponse response = CLIENT.execute(new HttpPost("http://localhost:1026/"));
        StatusLine statusLine = response.getStatusLine();
        HttpEntity entity = response.getEntity();

        String expectedTitle = "HTTP/1.1 405 Method not allowed";
        String actualTitle = statusLine.toString();
        assertEquals(expectedTitle, actualTitle);

        String expectedHeader = "Content-Type: text/html";
        String actualHeader = entity.getContentType().toString();
        assertEquals(expectedHeader, actualHeader);

        String expectedEntityContent = getMethodNotAllowed().replaceAll("\n", "\r\n");
        String actualEntityContent = IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8);
        assertEquals(expectedEntityContent, actualEntityContent);
    }

    @SneakyThrows
    @Test
    @DisplayName("Test, connect with server try to get exception 500 internal server error.")
    public void testConnectWithServerTryToGetException500InternalServerError() {
        Thread thread = new Thread(() -> {
            new Server(1027, "src\\test\\resources\\notExistPath").start();
        });
        thread.start();

        CloseableHttpResponse response = CLIENT.execute(new HttpGet("http://localhost:1027"));
        StatusLine statusLine = response.getStatusLine();
        HttpEntity entity = response.getEntity();

        String expectedTitle = "HTTP/1.1 500 Internal server error";
        String actualTitle = statusLine.toString();
        assertEquals(expectedTitle, actualTitle);

        String expectedHeader = "Content-Type: text/html";
        String actualHeader = entity.getContentType().toString();
        assertEquals(expectedHeader, actualHeader);

        String expectedEntityContent = getInternalServerError().replaceAll("\n", "\r\n");
        String actualEntityContent = IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8);
        assertEquals(expectedEntityContent, actualEntityContent);
    }

    private String getBadRequest() {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                  <title>Error 400</title>
                  <style type="text/css">
                		body {
                			background-color: red;
                			text-align: center;
                			margin-top: 250px;
                		}
                		h1 {
                			font-size: 64px;
                			color: white;
                		}
                	</style>
                </head>
                <body>
                <h1>Error 400: Bad Request</h1>
                                
                </body>
                                
                </html>
                """;
    }

    private String getFileNotFound() {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Error 404</title>
                    <style type="text/css">
                		body {
                			background-color: red;
                			text-align: center;
                			margin-top: 250px;
                		}
                		h1 {
                			font-size: 64px;
                			color: white;
                		}
                    </style>
                </head>
                <body>
                <h1>Error 404: File Not Found</h1>
                                
                </body>
                                
                </html>""";
    }

    private String getMethodNotAllowed() {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Error 405</title>
                    <style type="text/css">
                		body {
                			background-color: red;
                			text-align: center;
                			margin-top: 250px;
                		}
                		h1 {
                			font-size: 64px;
                			color: white;
                		}
                    </style>
                </head>
                <body>
                <h1>Error 405: Method not allowed</h1>
                                
                </body>
                                
                </html>""";
    }

    private String getInternalServerError() {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                  <title>Error 500</title>
                  <style type= "text/css">
                		body {
                			background-color: red;
                			text-align: center;
                			margin-top: 250px;
                		}
                		h1 {
                			font-size: 64px;
                			color: white;
                		}
                	</style>
                </head>
                <body>
                <h1>Error 500: Internal Server Error</h1>
                                
                </body>
                                
                </html>""";
    }

    static class CustomClient {
        public static String sendInvalidRequestAndGetResponse(int port) throws IOException {
            @Cleanup Socket socket = new Socket("localhost", port);
            String badRequest = "GET \\ Http/1.1\r\n\r\n";
            socket.getOutputStream().write(badRequest.getBytes());

            @Cleanup BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            StringBuilder builder = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\r\n");
            }
            return builder.toString();
        }
    }
}
