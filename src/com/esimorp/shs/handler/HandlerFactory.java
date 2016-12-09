package com.esimorp.shs.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.esimorp.shs.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HandlerFactory {
    public static List<HttpHandler> initHandlersWithServersJSON(String serversJson) throws IOException {
        ArrayList<HttpHandler> handlers = new ArrayList<>();
        JSONArray servers = JSON.parseArray(serversJson);
        for (int i = 0; i < servers.size(); i++) {
            JSONObject jsonObject = servers.getJSONObject(i);
            String documentRoot = jsonObject.getString("DocumentRoot");
            JSONArray hostsJsonArray = jsonObject.getJSONArray("hosts");
            String[] hosts = new String[hostsJsonArray.size()];
            hosts = hostsJsonArray.toArray(hosts);
            StaticFilesHandler staticFilesHandler = new StaticFilesHandler(documentRoot, Arrays.asList(hosts));
            handlers.add(staticFilesHandler);
        }
        return handlers;
    }
}
