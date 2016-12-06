package com.esimorp.shs.http;

import com.esimorp.shs.entity.Request;
import com.esimorp.shs.exceptions.HttpException;

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
        boolean isFirstLine = true;
        Request request = new Request();
        List<String> headerLines = new ArrayList<String>();
        try {
            while ((line = input.readLine()) != null) {
                if (isFirstLine) {
                    request.initRequestProps(line);
                    isFirstLine = false;
                    continue;
                }
                if (line.length() == 0) {
                    request.initRequestHeaders(headerLines);
                    break;
                } else {
                    headerLines.add(line);
                }
            }
        } catch (HttpException e) {
            int errorCode = e.getCode();
        } catch (Exception e) {
            int errorCode = 500;
            e.printStackTrace();
        }

        System.out.println(request);
        output.write("Hello World");
        output.flush();
        input.close();
        output.close();
    }
}
