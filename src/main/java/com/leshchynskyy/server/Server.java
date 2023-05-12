package com.leshchynskyy.server;

import com.leshchynskyy.handler.RequestHandler;

import lombok.*;

import java.io.BufferedReader;
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
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            while (true) {
                try (Socket socket = serverSocket.accept();
                     OutputStream outputStream = socket.getOutputStream();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                    RequestHandler handler = new RequestHandler();
                    handler.handle(reader, outputStream, sourcePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
