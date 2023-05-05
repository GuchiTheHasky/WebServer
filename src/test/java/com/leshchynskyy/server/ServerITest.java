package com.leshchynskyy.server;

import lombok.Cleanup;
import lombok.SneakyThrows;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ServerITest {
    // В мене вилітала ця помилка, тому запустив окремі потоки з різними портами, для кожного тесту.
    // Exception in thread "Thread-1" java.net.BindException: Address already in use: bind

    @SneakyThrows
    @Test
    @DisplayName("Test, Http GET with default client & verify response.")
    public void testHttpGetWithDefaultClientAndVerifyResponse() {
        CloseableHttpClient CLIENT = HttpClients.createDefault();
        Thread thread = new Thread(() -> new Server(9999, "src\\test\\resources\\webApp").start());
        thread.start();

        HttpGet httpGet = new HttpGet("http://localhost:9999/index.html");
        CloseableHttpResponse response = CLIENT.execute(httpGet);

        String expectedTitle = "HTTP/1.1 200 OK";
        assert response != null;
        String actualTitle = String.valueOf(response.getStatusLine());
        assertEquals(expectedTitle, actualTitle);

        String expectedHeader = "[Content-Type: text/html]";
        String actualHeader = Arrays.toString(response.getAllHeaders());
        assertEquals(expectedHeader, actualHeader);

        HttpEntity entity = response.getEntity();
        String pathToHtml = "src/test/resources/webApp/index.html";
        String htmlResponceExpectedContent = getContent(pathToHtml);
        String htmlResponceActualContent = EntityUtils.toString(entity);
        assertEquals(htmlResponceExpectedContent.trim(), htmlResponceActualContent.trim());
    }

    @SneakyThrows
    @Test
    @DisplayName("Test, Http GET with default client for Css file & verify response.")
    public void testHttpGetWithDefaultClientForCssFileAndVerifyResponse() {
        CloseableHttpClient CLIENT = HttpClients.createDefault();
        Thread thread = new Thread(() -> new Server(9998, "src\\test\\resources\\webApp").start());
        thread.start();
        HttpGet httpGet = new HttpGet("http://localhost:9998/css/style.css");
        CloseableHttpResponse response = CLIENT.execute(httpGet);

        String expectedTitle = "HTTP/1.1 200 OK";
        String actualTitle = String.valueOf(response.getStatusLine());
        assertEquals(expectedTitle, actualTitle);

        String expectedHeader = "[Content-Type: text/css]";
        String actualHeader = Arrays.toString(response.getAllHeaders());
        assertEquals(expectedHeader, actualHeader);

        HttpEntity entity = response.getEntity();
        String pathToCss = "src/test/resources/webApp/css/style.css";
        String cssResponceExpectedContent = getContent(pathToCss);
        String cssResponceActualContent = EntityUtils.toString(entity);
        assertEquals(cssResponceExpectedContent.trim(), cssResponceActualContent.trim());
    }

    @SneakyThrows
    @Test
    @DisplayName("Test, Http GET with default client for Css file & verify response.")
    public void testHttpGetWithDefaultClientForImageFileAndVerifyResponse() {
        CloseableHttpClient CLIENT = HttpClients.createDefault();
        Thread thread = new Thread(() -> new Server(9997, "src\\test\\resources\\webApp").start());
        thread.start();
        HttpGet httpGet = new HttpGet("http://localhost:9997/img/image.jpg");
        CloseableHttpResponse response = CLIENT.execute(httpGet);

        String expectedTitle = "HTTP/1.1 200 OK";
        String actualTitle = String.valueOf(response.getStatusLine());
        assertEquals(expectedTitle, actualTitle);

        String expectedHeader = "[Content-Type: image/jpeg]";
        String actualHeader = Arrays.toString(response.getAllHeaders());
        assertEquals(expectedHeader, actualHeader);
    }

    @SneakyThrows
    private String getContent(String path) {
        StringBuilder builder = new StringBuilder();
        @Cleanup BufferedReader reader = new BufferedReader(new FileReader(path));
        String line = "";
        while ((line = reader.readLine()) != null) {
            builder.append(line).append("\r\n");
        }
        return builder.toString();
    }
}
