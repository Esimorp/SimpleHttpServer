package com.esimorp.shs.handler;

import com.esimorp.shs.entity.Request;
import com.esimorp.shs.entity.response.Response;
import com.esimorp.shs.exceptions.HttpException;

import java.util.List;

public abstract class HttpHandler {
    private List<String> hosts;

    public HttpHandler(List<String> hosts) {
        this.hosts = hosts;
    }

    public boolean test(String host) {
        return hosts.contains(host);
    }

    public abstract Response doWithHttpRequest(Request request) throws HttpException;
}
