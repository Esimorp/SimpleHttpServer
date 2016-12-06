package com.esimorp.shs.entity.body;

/**
 * Created by Esimorp on 2016/12/6.
 */
public class PlainTextBody extends HttpBody {
    private String bodyContent;

    public PlainTextBody(String bodyContent) {
        this.bodyContent = bodyContent;
    }

    @Override
    public int getContentLength() {
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
