package com.esimorp.shs.entity;


import com.esimorp.shs.exceptions.MethodNotAllowException;

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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String separator = System.getProperty("line.separator");
        builder.append("Method : ").append(method).append(separator).append("Path : ").append(path).append(separator).append("Version : ").append(version).append(separator).append(headers.toString());
        return builder.toString();
    }

    public void initRequestHeaders(List<String> headerLines) {
        this.headers = new Headers(headerLines);
    }

    public void initRequestBody(List<String> bodyLines) {

    }


    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
