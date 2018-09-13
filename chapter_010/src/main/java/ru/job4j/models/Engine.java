package ru.job4j.models;

import java.util.Objects;

public class Engine {
    private int id;
    private String description;

    public Engine(String description) {
        this.description = description;
    }

    public Engine() {
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
