package com.leshchynskyy.server;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ServerITest {

    private final CloseableHttpClient CLIENT = HttpClients.createDefault();

    @BeforeAll
    static void init() {
        Thread thread = new Thread(() -> {
            new Server(1025, "src\\test\\resources\\webApp").start();
        });
        thread.start();
    }

    @Test
    @SneakyThrows
    @DisplayName("Test, connect to local port check title & Content-Type.")
    public void testConnectToLocalPortCheckHttpRequest() {
        CloseableHttpResponse response = CLIENT.execute(new HttpGet("http://localhost:1025/"));

        String expectedTitle = "HTTP/1.1 200 OK";
        String actualTitle = String.valueOf(response.getStatusLine());
        assertEquals(expectedTitle, actualTitle);

        String expectedHeader = "[Content-Type: text/html]";
        String actualHeader = Arrays.toString(response.getAllHeaders());
        assertEquals(expectedHeader, actualHeader);
    }

    @Test
    @SneakyThrows
    @DisplayName("Test, GET index.html;")
    public void testGetIndexHtmlCheckStatusLineHeaderFileContent() {
        CloseableHttpResponse response = CLIENT.execute(new HttpGet("http://localhost:1025/index.html"));

        StatusLine statusLine = response.getStatusLine();
        HttpEntity entity = response.getEntity();

        String expectedStatusLine = "HTTP/1.1 200 OK";
        String actualStatusLine = statusLine.toString();
        assertEquals(expectedStatusLine, actualStatusLine);

        String expectedHeader = "Content-Type: text/html";
        String actualHeader = entity.getContentType().toString();
        assertEquals(expectedHeader, actualHeader);

        String expectedEntityContent = IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8);
        String actualEntityContent = getIndexHtml().replaceAll("\n", "\r\n");
        assertEquals(actualEntityContent, expectedEntityContent);
    }

    @Test
    @SneakyThrows
    @DisplayName("Test, GET style.css;")
    public void testGetStyleCssCheckStatusLineHeaderFileContent() {
        CloseableHttpResponse response = CLIENT.execute(new HttpGet("http://localhost:1025/css/style.css"));

        StatusLine statusLine = response.getStatusLine();
        HttpEntity entity = response.getEntity();

        String expectedStatusLine = "HTTP/1.1 200 OK";
        String actualStatusLine = statusLine.toString();
        assertEquals(expectedStatusLine, actualStatusLine);

        String expectedHeader = "Content-Type: text/css";
        String actualHeader = entity.getContentType().toString();
        assertEquals(expectedHeader, actualHeader);

        String expectedEntityContent = IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8);
        String actualEntityContent = getStyleCss().replaceAll("\n", "\r\n");
        assertEquals(actualEntityContent, expectedEntityContent);
    }

    @Test
    @SneakyThrows
    @DisplayName("Test, GET image.jpg;")
    public void testGetImageCheckContentType() {
        CloseableHttpResponse response = CLIENT.execute(new HttpGet("http://localhost:1025/img/image.jpg"));

        StatusLine statusLine = response.getStatusLine();
        HttpEntity entity = response.getEntity();

        String expectedStatusLine = "HTTP/1.1 200 OK";
        String actualStatusLine = statusLine.toString();
        assertEquals(expectedStatusLine, actualStatusLine);

        String expectedHeader = "Content-Type: image/jpeg";
        String actualHeader = entity.getContentType().toString();
        assertEquals(expectedHeader, actualHeader);
    }

    private String getIndexHtml() {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <link rel="stylesheet" href="css/style.css">
                    <link rel="icon" type="image/x-icon" href="favicon.ico">
                    <title>LocalPage</title>
                </head>
                <body>
                <div class="container">
                    <label>
                        <input type="text" placeholder="Login">
                    </label>
                    <label>
                        <input type="password" placeholder="Password">
                    </label>
                    <button type="submit">Yes, I already have 18</button>

                </div>

                <div class="image-container">
                    <img src="img/image.jpg" alt="Sample Image">
                </div>

                </body>
                </html>""";
    }

    private String getStyleCss() {
        return """
                body {
                    height: 100vh;
                    display: flex;
                    flex-direction: column;
                    justify-content: center;
                    align-items: center;
                    background-color: #5fa3e0;
                }
                                
                .container {
                    background-color: #5fa3e0;
                    padding: 20px;
                    width: 300px;
                    text-align: center;
                }
                                
                input[type="text"],
                input[type="password"] {
                    width: 250px;
                    padding: 10px;
                    margin-bottom: 10px;
                    background-color: pink;
                    font-size: 18px;
                    text-align: center;
                }
                                
                input[type="text"]::placeholder,
                input[type="password"]::placeholder {
                    color: black;
                }
                                
                button[type="submit"] {
                    background-color: #29FF20;
                    padding: 10px;
                    width: 200px;
                    font-size: 18px;
                }
                """;
    }
}



