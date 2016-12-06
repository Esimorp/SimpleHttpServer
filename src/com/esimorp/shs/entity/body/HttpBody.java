package com.esimorp.shs.entity.body;


public abstract class HttpBody {
    public abstract int getContentLength();

    public abstract String getContentType();

    public abstract String toString();
}
