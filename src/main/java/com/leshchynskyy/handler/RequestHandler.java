package com.leshchynskyy.handler;

import com.leshchynskyy.entity.Request;
import com.leshchynskyy.enums.HttpStatusCode;
import com.leshchynskyy.io.RequestParser;
import com.leshchynskyy.io.ResourceReader;
import com.leshchynskyy.io.ResponceWriter;
import com.leshchynskyy.util.ServerException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class RequestHandler {
    public void handle(BufferedReader reader, OutputStream outputStream, String sourcePath) throws IOException {
        try {
            if (!isSourcePathFile(sourcePath)) {
                throw new ServerException(HttpStatusCode.INTERNAL_SERVER_ERROR);
            }

            Request request = RequestParser.parse(reader);
            System.out.println(request);

            if (!isValidHttpMethod(request)) {
                throw new ServerException(HttpStatusCode.METHOD_NOT_ALLOWED);
            }

            if (isBadRequest(request)) {
                throw new ServerException(HttpStatusCode.BAD_REQUEST);
            }

            List<String> sourceFilesPathList = ResourceReader.getFilesList(sourcePath);

            if (!isFileExist(request, sourceFilesPathList)) {
                throw new ServerException(HttpStatusCode.NOT_FOUND);
            }

            ResponceWriter responceWriter = new ResponceWriter();
            responceWriter.writeResponce(request, outputStream, sourcePath, sourceFilesPathList);
        } catch (ServerException e) {
            ResponceWriter.writeError(outputStream, e.getStatusCode());
        }
    }

    private boolean isSourcePathFile(String resourcePath) {
        return new File(resourcePath).exists();
    }

    private boolean isBadRequest(Request request) {
        try {
            new URI(request.getUri());
            return false;
        } catch (URISyntaxException e) {
            return true;
        }
    }

    private boolean isValidHttpMethod(Request request) {
        return request.getMethod().getEssence().equals("GET");
    }

    private boolean isFileExist(Request request, List<String> list) {
        String uri = request.getUri().substring(request.getUri().lastIndexOf("/") + 1);
        for (String s : list) {
            if (s.endsWith(uri)) {
                return true;
            }
        }
        return false;
    }
}
