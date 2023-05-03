package com.leshchynskyy.server;

import com.leshchynskyy.handler.MainHandler;

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
        @Cleanup ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            @Cleanup Socket socket = serverSocket.accept();
            @Cleanup OutputStream outputStream = socket.getOutputStream();
            @Cleanup BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            MainHandler handler = new MainHandler();
            handler.handle(reader, outputStream, sourcePath);

            if (socket.isInputShutdown() || socket.isOutputShutdown()) {
                socket.close();
                break;
            }
        }
    }
}
