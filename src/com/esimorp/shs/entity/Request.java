package com.esimorp.shs.entity;


import com.esimorp.shs.exceptions.MethodNotAllowException;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;

public class Request {
    private Headers headers;
    private HttpMethod method;
    private String path;
    private String version;

    public Request() {

    }

    public void initRequestProps(String firstLine) throws MethodNotAllowException {
        String[] tempArray = firstLine.split(" ");
        String methodString = tempArray[0];
        this.method = HttpMethod.parse(methodString);
        this.path = tempArray[1];
        this.version = tempArray[2];
    }

    public void initRequestHeaders(List<String> headerLines) {

    }

    public void initRequestBody(List<String> bodyLines) {

    }
}
