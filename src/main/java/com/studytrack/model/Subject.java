package com.studytrack.model;

public class Subject {

    private String name;
    private String code;

    public Subject(String name, String code) {
        setName(name);
        setCode(code);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Subject name cannot be empty.");
        }

        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Subject code cannot be empty.");
        }

        this.code = code;
    }

    @Override
    public String toString() {
        return code + " - " + name;
    }
}