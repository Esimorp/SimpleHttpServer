package com.esimorp.shs.http;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    public HttpServer(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        String line = null;
        while ((line = input.readLine()) != null) {
            System.out.println(line);
            if (line.length() == 0)
                break;
        }

        output.write("Hello World");
        output.flush();
        output.close();
    }
}
