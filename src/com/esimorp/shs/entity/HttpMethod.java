package com.esimorp.shs.entity;

import com.esimorp.shs.exceptions.MethodNotAllowException;

public enum HttpMethod {
    GET, POST, PUT, DELETE, HEAD, OPTIONS, TRACE, CONNECT;

    public static HttpMethod parse(String method) throws MethodNotAllowException {
        switch (method.toUpperCase()) {
            case "GET":
                return GET;
            case "POST":
                return POST;
            case "PUT":
                return PUT;
            case "DELETE":
                return DELETE;
            case "HEAD":
                return HEAD;
            case "OPTIONS":
                return OPTIONS;
            case "TRACE":
                return TRACE;
            case "CONNECT":
                return CONNECT;
        }
        throw new MethodNotAllowException();
    }
}
