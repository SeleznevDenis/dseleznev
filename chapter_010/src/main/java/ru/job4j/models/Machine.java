package ru.job4j.models;

import ru.job4j.interfaces.IEntity;

public class Machine implements IEntity {
    private int id;
    private String name;
    private Transmission transmission;
    private Engine engine;
    private GearBox gearBox;

    public Machine(String name, Transmission transmission, Engine engine, GearBox gearBox) {
        this.name = name;
        this.transmission = transmission;
        this.engine = engine;
        this.gearBox = gearBox;
    }

    public Machine(int id) {
        this.id = id;
    }

    public Machine() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public GearBox getGearBox() {
        return gearBox;
    }

    public void setGearBox(GearBox gearBox) {
        this.gearBox = gearBox;
    }
}
