package com.esimorp.shs.entity.body;


public class PlainTextBody extends HttpBody {
    public String bodyContent;

    public PlainTextBody(String bodyContent) {
        this.bodyContent = bodyContent;
    }

    @Override
    public int getContentLength() {
        return bodyContent.getBytes().length;
    }

    @Override
    public String getContentType() {
        return "application/x-download";
    }

    @Override
    public String toString() {
        return bodyContent;
    }
}
