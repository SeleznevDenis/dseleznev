package ru.job4j.annotationstest.models;

import ru.job4j.carplace.persistens.interfaces.IEntity;

public class Machine implements IEntity {
    private int id;
    private String name;
    private MTransmission mTransmission;
    private MEngine mEngine;
    private GearBox gearBox;

    public Machine(String name, MTransmission mTransmission, MEngine mEngine, GearBox gearBox) {
        this.name = name;
        this.mTransmission = mTransmission;
        this.mEngine = mEngine;
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

    public MTransmission getMTransmission() {
        return mTransmission;
    }

    public void setMTransmission(MTransmission mTransmission) {
        this.mTransmission = mTransmission;
    }

    public MEngine getMEngine() {
        return mEngine;
    }

    public void setMEngine(MEngine mEngine) {
        this.mEngine = mEngine;
    }

    public GearBox getGearBox() {
        return gearBox;
    }

    public void setGearBox(GearBox gearBox) {
        this.gearBox = gearBox;
    }
}
