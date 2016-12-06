package com.esimorp.shs;

import com.esimorp.shs.http.HttpServer;

import java.io.IOException;

public class Main {
    public static void main(String args[]) {
        try {
            new HttpServer(8081);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
