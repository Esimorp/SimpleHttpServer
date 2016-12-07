package com.esimorp.shs.utils;

import java.io.*;

public class FileUtil {
    public static String readFile(File file) throws IOException {
        StringBuilder builder = new StringBuilder();
        InputStream is = new FileInputStream(file);
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine();
        while (line != null) {
            builder.append(line);
            builder.append("\n");
            line = reader.readLine();
        }
        reader.close();
        is.close();
        return builder.toString();
    }
}