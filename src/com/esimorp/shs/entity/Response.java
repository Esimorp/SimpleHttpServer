package com.esimorp.shs.entity;

import com.esimorp.shs.entity.body.HttpBody;

public class Response {
    private Headers headers;
    private HttpBody body;
    private String version;
    private int code;
    private String message;

    public Response(String version, int code, String message, HttpBody body) {
        this.headers = new Headers();
        this.version = version;
        this.code = code;
        this.message = message;
        setBody(body);
    }


    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public HttpBody getBody() {
        return body;
    }

    public void setBody(HttpBody body) {
        this.body = body;

        if (this.headers.containsKey("Content-Length")) {
            this.headers.get("Content-Length").setValue(String.valueOf(body.getContentLength()));
        } else {
            Header header = new Header("Content-Length", String.valueOf(body.getContentLength()));
            this.headers.put("Content-Length", header);
        }

        if (this.headers.containsKey("Content-Type")) {
            this.headers.get("Content-Type").setValue(String.valueOf(body.getContentType()));
        } else {
            Header header = new Header("Content-Type", String.valueOf(body.getContentType()));
            this.headers.put("Content-Type", header);
        }
    }

    @Override
    public String toString() {
        String builder = version + " " + code + " " +
                message + "\r\n" + headers.toString() +
                "\r\n" + body.toString();
        return builder;
    }
}
