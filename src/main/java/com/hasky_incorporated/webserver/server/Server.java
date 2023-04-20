package com.hasky_incorporated.webserver.server;

import com.hasky_incorporated.webserver.handlers.ValidationHandler;
import lombok.*;

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
        }
    }


}
