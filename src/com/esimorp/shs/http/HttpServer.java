package com.esimorp.shs.http;

import com.esimorp.shs.entity.Request;
import com.esimorp.shs.entity.response.HttpErrorResponse;
import com.esimorp.shs.entity.response.Response;
import com.esimorp.shs.exceptions.HttpException;
import com.esimorp.shs.handler.HttpHandler;
import com.esimorp.shs.handler.StaticFilesHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class HttpServer {
    List<HttpHandler> handlers;

    public HttpServer(int port, List<HttpHandler> handlers) throws IOException {
        setHandlers(handlers);
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            final Socket socket = serverSocket.accept();
            new Thread(() -> {
                BufferedReader input = null;
                BufferedWriter output = null;
                try {
                    input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                try {
                    String line = null;
                    boolean isFirstLine = true;
                    Request request = new Request();
                    List<String> headerLines = new ArrayList<String>();
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
                    String host = request.getHeaders().get("Host").getValue();
                    for (HttpHandler handler : this.handlers) {
                        if (handler.test(host)) {
                            Response response = handler.doWithHttpRequest(request);
                            output.write(response.toString());
                            return;
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (HttpException e) {
                    try {
                        Response response = new HttpErrorResponse(e.getCode());
                        output.write(response.toString());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } finally {
                    try {
                        output.flush();
                        input.close();
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public void setHandlers(List<HttpHandler> handlers) {
        this.handlers = handlers;
    }
}
