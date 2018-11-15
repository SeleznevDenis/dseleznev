package ru.job4j.dto;

import ru.job4j.persistens.models.*;

import java.util.List;

/**
 * Описывает все данные для заполнения формы ввода объявления.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class AdvertFormDTO {
    private List<Body> bodies;
    private List<Brand> brands;
    private List<Color> colors;
    private List<DriveUnit> driveUnits;
    private List<Engine> engines;
    private List<Transmission> transmissions;
    private List<Wheel> wheels;

    public List<Body> getBodies() {
        return bodies;
    }

    public void setBodies(List<Body> bodies) {
        this.bodies = bodies;
    }

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }

    public List<Color> getColors() {
        return colors;
    }

    public void setColors(List<Color> colors) {
        this.colors = colors;
    }

    public List<DriveUnit> getDriveUnits() {
        return driveUnits;
    }

    public void setDriveUnits(List<DriveUnit> driveUnits) {
        this.driveUnits = driveUnits;
    }

    public List<Engine> getEngines() {
        return engines;
    }

    public void setEngines(List<Engine> engines) {
        this.engines = engines;
    }

    public List<Transmission> getTransmissions() {
        return transmissions;
    }

    public void setTransmissions(List<Transmission> transmissions) {
        this.transmissions = transmissions;
    }

    public List<Wheel> getWheels() {
        return wheels;
    }

    public void setWheels(List<Wheel> wheels) {
        this.wheels = wheels;
    }
}
