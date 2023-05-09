package com.leshchynskyy.io;

import com.leshchynskyy.enums.HttpStatusCode;
import com.leshchynskyy.util.ServerException;
import lombok.SneakyThrows;

import java.io.*;
import java.util.*;

public class ResourceReader {
    private static final String CRLF = "" + (char) 0x0D + (char) 0x0A;

    public static List<String> getFilesList(String resourcePath) {
        List<String> filesList = new ArrayList<>();
        File directory = new File(resourcePath);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String relativePath = directory.toURI().relativize(file.toURI()).getPath();
                    filesList.add(relativePath);
                } else if (file.isDirectory()) {
                    List<String> subdirectoryFiles = null;
                    try {
                        subdirectoryFiles = getFilesList(file.getCanonicalPath());
                    } catch (IOException e) {
                        throw new ServerException(HttpStatusCode.INTERNAL_SERVER_ERROR);
                    }
                    for (String subdirectoryFile : subdirectoryFiles) {
                        String relativePath = file.getName() + '/' + subdirectoryFile;
                        filesList.add(relativePath);
                    }
                }
            }
        }
        return filesList;
    }

    @SneakyThrows
    String getContent(BufferedReader reader) {
        StringBuilder contentBuilder = new StringBuilder();
        String content = "";
        while ((content = reader.readLine()) != null) {
            contentBuilder.append(content).append(CRLF);
            if (content.equals("")) {
                break;
            }
        }
        return contentBuilder.toString();
    }
}