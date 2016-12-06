package com.esimorp.shs.entity;

import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

public class Headers extends HashMap<String, Header> {
    public Headers() {

    }

    public Headers(List<String> headerLines) {
        for (String line : headerLines) {
            Header header = new Header(line);
            put(header.getName(), header);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String separator = System.getProperty("line.separator");
        this.forEach((key, header) -> {
            builder.append(header.toString()).append(separator);
        });
        return builder.toString();
    }
}
