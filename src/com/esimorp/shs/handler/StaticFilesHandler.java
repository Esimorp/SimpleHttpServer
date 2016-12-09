package com.esimorp.shs.handler;

import com.esimorp.shs.entity.Request;
import com.esimorp.shs.entity.response.Response;
import com.esimorp.shs.entity.body.StaticFileBody;
import com.esimorp.shs.exceptions.HttpException;

import java.io.File;
import java.util.List;

public class StaticFilesHandler extends HttpHandler {
    private String documentRoot;

    public StaticFilesHandler(String documentRoot, List<String> hosts) {
        super(hosts);
        this.documentRoot = documentRoot;
    }

    public StaticFilesHandler(List<String> hosts) {
        super(hosts);
        this.documentRoot = ".";
    }

    @Override
    public Response doWithHttpRequest(Request request) throws HttpException {
        String path = request.getPath();
        File file = new File(documentRoot + path);
        StaticFileBody body = new StaticFileBody(file);
        return new Response("HTTP/1.1", 200, "OK", body);
    }

    public String getDocumentRoot() {
        return documentRoot;
    }

    public void setDocumentRoot(String documentRoot) {
        this.documentRoot = documentRoot;
    }
}
