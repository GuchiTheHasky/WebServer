package com.leshchynskyy.io;

import lombok.Cleanup;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResourceReaderTest {
    private final static String expectedContent = """
            GET /hello.htm HTTP/1.1
            User-Agent: Mozilla/4.0 (compatible; MSIE5.01; Windows NT)
            Host: www.tutorialspoint.com
            Accept-Language: en-us
            Accept-Encoding: gzip, deflate
            Connection: Keep-Alive
            """;
    private final ResourceReader resourceReader = new ResourceReader();
    @SneakyThrows
    @Test
    @DisplayName("Test, get content from source stream.")
    public void testGetContentFromSourceStream () {
        @Cleanup BufferedReader reader = new BufferedReader
                (new FileReader("src\\test\\resources\\requestfortest"));
        String actualContent = resourceReader.getContent(reader);
        assertEquals(expectedContent, actualContent.replaceAll("\r\n", "\n"));
    }

    @Test
    public void test() { //TODO
        List<String> list = resourceReader.getFilesList("src\\main\\resources\\webapp");
        System.out.println(list);
    }
}
