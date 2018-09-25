package ru.job4j.annotationstest.models;

public class MEngine {
    private int id;
    private String description;

    public MEngine(String description) {
        this.description = description;
    }

    public MEngine() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
