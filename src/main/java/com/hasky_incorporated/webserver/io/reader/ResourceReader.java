package com.hasky_incorporated.webserver.io.reader;


import com.hasky_incorporated.webserver.handlers.ExceptionHandler;
import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class ResourceReader {

    @SneakyThrows
    public String getContent(BufferedReader reader) {
        StringBuilder builder = new StringBuilder();
        String content = "";
        while ((content = reader.readLine()) != null) {
            builder.append(content).append("\r\n");
            if (content.equals("")) {
                break;
            }
        }
        return builder.toString();
    }

    public void outputStream(OutputStream outputStream, String fileName) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        @Cleanup BufferedInputStream bisCss = new BufferedInputStream(Files.newInputStream(Paths.get(fileName)));
        while ((bytesRead = bisCss.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
    }


    public static void validateResource(String resourcePath) {
        Path path = Paths.get(resourcePath);
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            throw new ExceptionHandler("Current path or directory " + resourcePath + " doesn't exist.");
        }
        List<File> resourcesList = getFilesList(resourcePath);
        for (File currenrFile : resourcesList) {
            if (currenrFile.length() == 0) {
                throw new ExceptionHandler("Current file: " + currenrFile + " is empty.");
            }
        }
    }

    public static List<File> getFilesList(String resourcePath) {
        List<File> filesList = new ArrayList<>();
        File directory = new File(resourcePath);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    filesList.add(file);
                } else if (file.isDirectory()) {
                    filesList.addAll(getFilesList(file.getAbsolutePath()));
                }
            }
        }
        return filesList;
    }
}
