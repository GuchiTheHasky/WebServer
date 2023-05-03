package com.leshchynskyy.io;

import com.leshchynskyy.entity.Request;
import com.leshchynskyy.enums.HttpStatusCode;
import com.leshchynskyy.util.ServerException;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.apache.tika.Tika;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ResponceWriter {
    private static final String CRLF = "" + (char) 0x0D + (char) 0x0A;
    private final ResourceReader resourceReader = new ResourceReader();
    private final Tika formatExtractor = new Tika();
    private boolean isBadRequest = true;

    @SneakyThrows
    public void responce(Request request, OutputStream outputStream, String resourcePath) {
        List<String> resourceFilePathList = resourceReader.getFilesList(resourcePath);
        for (String path : resourceFilePathList) {
            String fileFormat = formatExtractor.detect(new File(resourcePath, path));
            if (request.getUri().endsWith(path)) {
                isBadRequest = false;
                httpTitle(outputStream);
                outputStream.write(("Content-Type: " + fileFormat).getBytes());
                crlf(outputStream);
                String fileBodyPath = resourcePath + File.separator + path;
                sendResponceBody(outputStream, fileBodyPath);
            }
        }
        if (isBadRequest) {
            throw ServerException.fileNotFound(outputStream);
        }
    }

    private void crlf(OutputStream outputStream) throws IOException {
        outputStream.write(CRLF.getBytes());
        outputStream.write(CRLF.getBytes());
    }

    private void httpTitle(OutputStream outputStream) throws IOException {
        outputStream.write("HTTP/1.1 ".getBytes());
        outputStream.write((HttpStatusCode.OK.getCode() + HttpStatusCode.OK.getStatus()).getBytes());
        outputStream.write(CRLF.getBytes());
    }

    @SneakyThrows
    private void sendResponceBody(OutputStream outputStream, String fileName) {
        byte[] buffer = new byte[1024];
        int bytesRead;
        @Cleanup BufferedInputStream bisCss = new BufferedInputStream(Files.newInputStream(Paths.get(fileName)));
        while ((bytesRead = bisCss.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
    }
}
