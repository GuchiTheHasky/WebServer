package com.leshchynskyy.handler;

import com.leshchynskyy.entity.Request;
import com.leshchynskyy.enums.HttpStatusCode;
import com.leshchynskyy.io.RequestParser;
import com.leshchynskyy.io.ResourceReader;
import com.leshchynskyy.io.ResponceWriter;
import com.leshchynskyy.util.ServerException;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class MainHandler {
    public void handle(BufferedReader reader, OutputStream outputStream, String sourcePath) {
        try {
            validateSourcePath(sourcePath);

            RequestParser parser = new RequestParser();
            Request request = parser.parse(reader);

            validateHttpMethod(request);

            List<String> sourceFilesPathList = ResourceReader.getFilesList(sourcePath);
            isFileExist(request, sourceFilesPathList);

            ResponceWriter responceWriter = new ResponceWriter();
            responceWriter.writeResponce(request, outputStream, sourcePath, sourceFilesPathList);
        } catch (ServerException e) {
            ResponceWriter.writeError(outputStream, e.getStatusCode());
        }
    }

    private void validateSourcePath(String resourcePath) {
        Path path = Paths.get(resourcePath);
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            throw new ServerException(HttpStatusCode.BAD_REQUEST);
        }
    }

    private void validateHttpMethod(Request request) {
        if (!request.getMethod().getEssence().equals("GET")) {
            throw new ServerException(HttpStatusCode.METHOD_NOT_ALLOWED);
        }
    }

    private boolean isFileExist(Request request, List<String> list) {
        String uri = request.getUri().substring(request.getUri().lastIndexOf("/") + 1);
        for (String s : list) {
            if (s.endsWith(uri)) {
                return true;
            }
        }
        throw new ServerException(HttpStatusCode.NOT_FOUND);
    }
}
