package com.leshchynskyy.io;

import com.leshchynskyy.entity.Request;
import com.leshchynskyy.enums.HttpStatusCode;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.apache.tika.Tika;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ResponceWriter {
    private static final String ERROR_PAGE_PATH = "src\\main\\resources\\errorpage\\";
    private static final String CRLF = "\r\n";
    private final String SOURCE_FILES =
            "File list:\n" + ResourceReader.getFilesList("src\\test\\resources\\webApp");

    private final Tika FORMAT_EXTRACTOR = new Tika();

    @SneakyThrows
    public void writeResponce(Request request, OutputStream outputStream, String resourcePath, List<String> paths) {
        if (request.getUri().endsWith("/")) {
            writeAvailableFileList(outputStream);
        }
        for (String testPath : paths) {
            String testFileFormat = FORMAT_EXTRACTOR.detect(new File(resourcePath, testPath));

            if (request.getUri().endsWith(testPath)) {
                statusLine(outputStream);
                outputStream.write(("Content-Type: " + testFileFormat).getBytes());
                crlf(outputStream);

                String fileBodyPath = resourcePath + File.separator + testPath;

                sendResponceBody(outputStream, fileBodyPath);
            }
        }
    }

    public static void writeError(OutputStream outputStream, HttpStatusCode statusCode) throws IOException {
        List<String> errorPages = ResourceReader.getFilesList(ERROR_PAGE_PATH);
        for (String path : errorPages) {
            String code = String.valueOf(statusCode.getCode());
            if (path.startsWith(code)) {
                outputStream.write("HTTP/1.1 ".getBytes());
                outputStream.write((statusCode.getCode() + " " + statusCode.getStatus()).getBytes());
                outputStream.write(CRLF.getBytes());
                outputStream.write("Content-Type: text/html".getBytes());
                outputStream.write(CRLF.getBytes());
                outputStream.write(CRLF.getBytes());
                sendResponceBody(outputStream, ERROR_PAGE_PATH + path);
            }
        }
    }

    private void crlf(OutputStream outputStream) throws IOException {
        outputStream.write(CRLF.getBytes());
        outputStream.write(CRLF.getBytes());
    }

    @SneakyThrows
    private void writeAvailableFileList(OutputStream outputStream) {
        defaultTitle(outputStream);
        outputStream.write(SOURCE_FILES.getBytes());
    }

    private static void defaultTitle(OutputStream outputStream) throws IOException {
        outputStream.write(("HTTP/1.1 " + HttpStatusCode.OK.getCode() + " " + HttpStatusCode.OK.getStatus()).getBytes());
        outputStream.write(CRLF.getBytes());
        outputStream.write("Content-Type: text/html".getBytes());
        outputStream.write(CRLF.getBytes());
        outputStream.write(CRLF.getBytes());
    }

    private void statusLine(OutputStream outputStream) throws IOException {
        outputStream.write(("HTTP/1.1 " + HttpStatusCode.OK.getCode() + " " + HttpStatusCode.OK.getStatus()).getBytes());
        outputStream.write(CRLF.getBytes());
    }

    @SneakyThrows
    private static void sendResponceBody(OutputStream outputStream, String fileName) {
        byte[] buffer = new byte[1024];
        int bytesRead;
        @Cleanup BufferedInputStream bisCss = new BufferedInputStream(Files.newInputStream(Paths.get(fileName)));
        while ((bytesRead = bisCss.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
    }
}


