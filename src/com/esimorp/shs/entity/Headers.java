package com.esimorp.shs.entity;

import java.util.HashMap;
import java.util.List;

public class Headers extends HashMap<String, Header> {
    public Headers(List<String> headerLines) {
        for (String line : headerLines) {
            Header header = new Header(line);
            put(header.getName(), header);
        }
    }
}
