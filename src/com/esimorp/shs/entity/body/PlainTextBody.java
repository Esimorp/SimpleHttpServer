package com.esimorp.shs.entity.body;


public class PlainTextBody extends HttpBody {
    public String bodyContent;

    public PlainTextBody(String bodyContent) {
        this.bodyContent = bodyContent;
    }

    @Override
    public long getContentLength() {
        return bodyContent.getBytes().length;
    }

    @Override
    public String getContentType() {
        return "text/plain";
    }

    @Override
    public String toString() {
        return bodyContent;
    }
}
