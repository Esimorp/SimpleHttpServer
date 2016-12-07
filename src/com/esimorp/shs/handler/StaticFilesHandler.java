package com.esimorp.shs.handler;

import com.esimorp.shs.entity.Request;
import com.esimorp.shs.entity.response.Response;
import com.esimorp.shs.entity.body.StaticFileBody;
import com.esimorp.shs.exceptions.HttpException;

import java.io.File;

public class StaticFilesHandler extends HttpHandler {
    @Override
    public Response doWithHttpRequest(Request request) throws HttpException {
        String path = request.getPath();
        File file = new File("." + path);
        StaticFileBody body = new StaticFileBody(file);
        return new Response("HTTP/1.1", 200, "OK", body);
    }
}
