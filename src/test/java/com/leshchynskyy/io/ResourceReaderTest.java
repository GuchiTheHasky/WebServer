package com.leshchynskyy.io;

import lombok.Cleanup;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResourceReaderTest {
    private final static String EXPECTED_CONTENT = """
            GET /hello.htm HTTP/1.1
            User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)
            Host: www.tutorialspoint.com
            Accept-Language: en-us
            Accept-Encoding: gzip, deflate
            Connection: Keep-Alive
            """;
    private final ResourceReader READER = new ResourceReader();

    @SneakyThrows
    @Test
    @DisplayName("Test, get content from source stream.")
    public void testGetContentFromSourceStream() {
        @Cleanup BufferedReader reader = new BufferedReader
                (new FileReader("src\\test\\resources\\requestfortest"));
        String actualContent = READER.getContent(reader);
        assertEquals(EXPECTED_CONTENT, actualContent.replaceAll("\r\n", "\n"));
    }

    @Test
    @DisplayName("Test, get file list from source root.")
    public void testGetFileListFromSourceRoot() {
        List<String> actualList = ResourceReader.getFilesList("src\\test\\resources\\webApp");
        int expectedCount = 4;
        int actualCount = actualList.size();
        assertEquals(expectedCount, actualCount);

        List<String> expectedList = new ArrayList<>(
                Arrays.asList("css/style.css",
                        "favicon.ico",
                        "img/image.jpg",
                        "index.html"));

        assertEquals(expectedList.get(0), actualList.get(0));
        assertEquals(expectedList.get(1), actualList.get(1));
        assertEquals(expectedList.get(2), actualList.get(2));
        assertEquals(expectedList.get(3), actualList.get(3));
    }
}
