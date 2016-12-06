package com.esimorp.shs.exceptions;

/**
 * Created by Esimorp on 2016/12/6.
 */
public class MethodNotAllowException extends HttpException {
    public MethodNotAllowException() {
        super(405, "Method Not Allow");
    }
}
