package com.esimorp.shs.entity;

public class Header {
    private String name;
    private String value;

    public Header(String line) {
        String[] tempArray = line.split(":");
        this.name = tempArray[0].trim();
        this.value = tempArray[1].trim();
    }

    public Header(String name, String value) {
        this.name = name;
        this.value = value;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return name + ":" + value;
    }
}
