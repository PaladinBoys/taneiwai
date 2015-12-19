package com.taneiwai.app.model;

public class Subject {

    public String name;
    public String content;
    public boolean haveAffix;

    public Subject(String name, String content, boolean haveAffix) {
        this.name = name;
        this.haveAffix = haveAffix;
        this.content = content;
    }
}