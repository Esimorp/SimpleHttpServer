package com.esimorp.shs.exceptions;

/**
 * Created by Esimorp on 2016/12/6.
 */
public class HttpException extends Exception {
    private int code;
    private String message;

    public HttpException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
