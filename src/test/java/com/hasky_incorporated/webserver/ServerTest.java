package com.hasky_incorporated.webserver;

import com.hasky_incorporated.webserver.handlers.ExceptionHandler;
import com.hasky_incorporated.webserver.handlers.ValidationHandler;
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

public class ServerTest {
    CloseableHttpClient httpClient = HttpClients.createDefault();

    @SneakyThrows
    @Test
    @DisplayName("Test, Http GET with default client & verify response.")
    public void testHttpGetWithDefaultClientAndVerifyResponse() {

        HttpGet httpGet = new HttpGet("http://localhost:1024/");
        CloseableHttpResponse response = httpClient.execute(httpGet);

        String expectedTitle = "HTTP/1.1 200 OK";
        String actualTitle = String.valueOf(response.getStatusLine());
        assertEquals(expectedTitle, actualTitle);

        String expectedHeader = "[Content-Type: text/html]";
        String actualHeader = Arrays.toString(response.getAllHeaders());
        assertEquals(expectedHeader, actualHeader);

        HttpEntity entity = response.getEntity();
        String pathToHtml = "src/main/resources/webApp/index.html";
        String htmlResponceExpectedContent = getContent(pathToHtml);
        String htmlResponceActualContent = EntityUtils.toString(entity);
        assertEquals(htmlResponceExpectedContent.trim(), htmlResponceActualContent.trim());
    }

    @SneakyThrows
    @Test
    @DisplayName("Test, Http GET with default client for Css file & verify response.")
    public void testHttpGetWithDefaultClientForCssFileAndVerifyResponse() {
        HttpGet httpGet = new HttpGet("http://localhost:1024/css/style.css");
        CloseableHttpResponse response = httpClient.execute(httpGet);

        String expectedTitle = "HTTP/1.1 200 OK";
        String actualTitle = String.valueOf(response.getStatusLine());
        assertEquals(expectedTitle, actualTitle);

        String expectedHeader = "[Content-Type: text/css]";
        String actualHeader = Arrays.toString(response.getAllHeaders());
        assertEquals(expectedHeader, actualHeader);

        HttpEntity entity = response.getEntity();
        String pathToCss = "src/main/resources/webApp/css/style.css";
        String cssResponceExpectedContent = getContent(pathToCss);
        String cssResponceActualContent = EntityUtils.toString(entity);
        assertEquals(cssResponceExpectedContent.trim(), cssResponceActualContent.trim());
    }

    @SneakyThrows
    @Test
    @DisplayName("Test, Http GET with default client for Css file & verify response.")
    public void testHttpGetWithDefaultClientForImageFileAndVerifyResponse() {
        HttpGet httpGet = new HttpGet("http://localhost:1024/img/image.jpg");
        CloseableHttpResponse response = httpClient.execute(httpGet);

        String expectedTitle = "HTTP/1.1 200 OK";
        String actualTitle = String.valueOf(response.getStatusLine());
        assertEquals(expectedTitle, actualTitle);

        String expectedHeader = "[Content-Type: img/jpeg]";
        String actualHeader = Arrays.toString(response.getAllHeaders());
        assertEquals(expectedHeader, actualHeader);
    }

    @Test
    @DisplayName("Test, ValidationHandler with incorrect port.")
    public void testValidationHandlerWithIncorrectPort() {
        Throwable thrown = assertThrows(ExceptionHandler.class, () -> {
            ValidationHandler.validateAttributes(777, "src\\main\\resources\\webApp");
        });
        assertNotNull(thrown.getMessage());
        String expected = "Incorrect port, try between 1024 and 65535.";
        String actual = thrown.getMessage();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("testValidationHandlerWithNonexistentPath")
    public void testValidationHandlerWithNonexistentPath() {
        Throwable thrown = assertThrows(ExceptionHandler.class, () -> {
            ValidationHandler.validateAttributes(1024, "path");
        });
        assertNotNull(thrown.getMessage());
        String expected = "Current path or directory path doesn't exist.";
        String actual = thrown.getMessage();
        assertEquals(expected, actual);
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
