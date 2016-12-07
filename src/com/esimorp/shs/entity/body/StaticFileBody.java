package com.esimorp.shs.entity.body;

import com.esimorp.shs.exceptions.NotFoundException;
import com.esimorp.shs.utils.FileUtil;

import java.io.*;

public class StaticFileBody extends HttpBody {
    private File file;
    private String fileString;

    public StaticFileBody(File file) throws NotFoundException {
        setFile(file);
    }

    @Override
    public long getContentLength() {
        return fileString.getBytes().length;
    }

    @Override
    public String getContentType() {
        return "text/html";
    }

    @Override
    public String toString() {
        return fileString;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) throws NotFoundException {
        try {
            this.file = file;
            this.fileString = FileUtil.readFile(file);
        } catch (IOException e) {
            throw new NotFoundException();
        }
    }
}
