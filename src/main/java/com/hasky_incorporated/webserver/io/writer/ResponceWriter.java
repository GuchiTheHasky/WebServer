package com.hasky_incorporated.webserver.io.writer;

import com.hasky_incorporated.webserver.enums.HttpStatus;
import com.hasky_incorporated.webserver.enums.ServerTools;
import com.hasky_incorporated.webserver.etities.Request;
import com.hasky_incorporated.webserver.io.reader.ResourceReader;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.OutputStream;


public class ResponceWriter {
    private final String CRLF = "" + (char) 0x0D + (char) 0x0A;

    @SneakyThrows
    public void writeResponce(Request request, OutputStream outputStream, String resourcePath){
        switch (request.getUri()) {
            case "/": {
                httpTitle(outputStream);
                outputStream.write("Content-Type: text/html".getBytes());
                crlf(outputStream);
                String path = resourcePath + ServerTools.HTML.getPath();
                ResourceReader resourceReader = new ResourceReader();
                resourceReader.outputStream(outputStream, path);
            }
            break;
            case "/css/style.css": {
                httpTitle(outputStream);
                outputStream.write("Content-Type: text/css".getBytes());
                crlf(outputStream);
                String path = resourcePath + ServerTools.CSS.getPath();
                ResourceReader resourceReader = new ResourceReader();
                resourceReader.outputStream(outputStream, path);
            }
            break;
            case "/img/image.jpg": {
                httpTitle(outputStream);
                outputStream.write("Content-Type: img/jpeg".getBytes());
                crlf(outputStream);
                String path = resourcePath + ServerTools.IMG.getPath();
                ResourceReader resourceReader = new ResourceReader();
                resourceReader.outputStream(outputStream, path);
            }
            break;
        }
    }

    private void crlf(OutputStream outputStream) throws IOException {
        outputStream.write(CRLF.getBytes());
        outputStream.write(CRLF.getBytes());
    }

    private void httpTitle(OutputStream outputStream) throws IOException {
        outputStream.write("HTTP/1.1".getBytes());
        outputStream.write(HttpStatus.OK.getStatus().getBytes());
        outputStream.write(CRLF.getBytes());
    }
}
