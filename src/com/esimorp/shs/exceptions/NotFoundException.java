package com.esimorp.shs.exceptions;

public class NotFoundException extends HttpException {
    public NotFoundException() {
        super(404, "Not Found");
    }

    public NotFoundException(String message) {
        super(404, message);
    }
}
