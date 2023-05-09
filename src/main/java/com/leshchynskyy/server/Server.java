package com.leshchynskyy.server;

import com.leshchynskyy.enums.HttpStatusCode;
import com.leshchynskyy.handler.MainHandler;

import com.leshchynskyy.io.ResponceWriter;
import lombok.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Server {
    private int port;
    private String sourcePath;

    @SneakyThrows
    public void start() {

        try (ServerSocket serverSocket = new ServerSocket(port);
             Socket socket = serverSocket.accept();
             OutputStream outputStream = socket.getOutputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            try {
                while (true) {
                    MainHandler handler = new MainHandler();
                    handler.handle(reader, outputStream, sourcePath);

                    if (socket.isInputShutdown() || socket.isOutputShutdown()) {
                        socket.close();
                        break;
                    }

                }
            } catch (IOException e) {
                ResponceWriter.writeError(outputStream, HttpStatusCode.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
