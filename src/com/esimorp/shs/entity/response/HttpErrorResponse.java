package com.esimorp.shs.entity.response;


import com.esimorp.shs.entity.body.PlainTextBody;

public class HttpErrorResponse extends Response {
    public HttpErrorResponse(int code) {
        super("HTTP/1.1", code, "Not Found", new PlainTextBody("Not Found"));
    }
}
