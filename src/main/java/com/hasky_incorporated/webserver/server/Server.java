package com.hasky_incorporated.webserver.server;

import com.hasky_incorporated.webserver.handlers.MainHandler;
import com.hasky_incorporated.webserver.handlers.ValidationHandler;
import lombok.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
            ValidationHandler.isAccepted(socket);
            @Cleanup BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            @Cleanup BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            MainHandler handler = new MainHandler();
            handler.handle(reader, writer, sourcePath);
        }
    }


}
