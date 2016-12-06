package com.esimorp.shs.http;

import com.esimorp.shs.entity.Request;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class HttpServer {

    public HttpServer(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));


        String line = null;
        int index = 0;
        boolean isFirstLine = true;
        Request request = new Request();
        List<String> headerLines = new ArrayList<String>();
        while ((line = input.readLine()) != null) {
            if (isFirstLine) {
                request.initRequestProps(line);
                isFirstLine = false;
            }
            headerLines.add(line);
            if (line.length() == 0) {
                request.initRequestHeaders(headerLines);
                break;
            }
            index++;
        }

        output.write("Hello World");
        output.flush();
        input.close();
        output.close();
    }
}
