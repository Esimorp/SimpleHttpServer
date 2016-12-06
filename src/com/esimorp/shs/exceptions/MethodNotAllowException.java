package com.esimorp.shs.exceptions;

public class MethodNotAllowException extends HttpException {
    public MethodNotAllowException() {
        super(405, "Method Not Allow");
    }
}
