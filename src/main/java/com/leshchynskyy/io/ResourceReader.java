package com.leshchynskyy.io;

import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ResourceReader {
    private static final String CRLF = "" + (char) 0x0D + (char) 0x0A;

    @SneakyThrows
    public void outputStream(OutputStream outputStream, String fileName) {
        byte[] buffer = new byte[1024];
        int bytesRead;
        @Cleanup BufferedInputStream bisCss = new BufferedInputStream(Files.newInputStream(Paths.get(fileName)));
        while ((bytesRead = bisCss.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
    }

    @SneakyThrows
    public List<String> getFilesList(String resourcePath) {
        List<String> filesList = new ArrayList<>();
        File directory = new File(resourcePath);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String relativePath = directory.toURI().relativize(file.toURI()).getPath();
                    filesList.add(relativePath);
                } else if (file.isDirectory()) {
                    List<String> subdirectoryFiles = getFilesList(file.getCanonicalPath());
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