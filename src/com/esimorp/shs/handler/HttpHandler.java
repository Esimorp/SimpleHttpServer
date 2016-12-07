package com.esimorp.shs.handler;

import com.esimorp.shs.entity.Request;
import com.esimorp.shs.entity.response.Response;
import com.esimorp.shs.exceptions.HttpException;

public abstract class HttpHandler {
    public abstract Response doWithHttpRequest(Request request) throws HttpException;
}
