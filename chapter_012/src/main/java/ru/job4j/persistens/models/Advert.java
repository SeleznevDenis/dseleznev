package ru.job4j.persistens.models;

import ru.job4j.persistens.interfaces.IEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Описывает объявление.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
@Entity
@Table(name = "advert")
public class Advert implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;
    private int engineValue;
    private int cost;
    private Timestamp releaseDate;
    private int mileage;
    private String photo;
    private Timestamp advertDate;

    @ManyToOne
    @JoinColumn(name = "body_fk")
    private Body body;

    @ManyToOne
    @JoinColumn(name = "color_fk")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "drive_unit_fk")
    private DriveUnit driveUnit;

    @ManyToOne
    @JoinColumn(name = "engine_fk")
    private Engine engine;

    @ManyToOne
    @JoinColumn(name = "transmission_fk")
    private Transmission transmission;

    @ManyToOne
    @JoinColumn(name = "wheel_fk")
    private Wheel wheel;

    @ManyToOne
    @JoinColumn(name = "user_fk")
    private User user;

    @ManyToOne
    @JoinColumn(name = "model_fk")
    private Model model;

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

    public int getEngineValue() {
        return engineValue;
    }

    public void setEngineValue(int engineValue) {
        this.engineValue = engineValue;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Timestamp getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Timestamp releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public DriveUnit getDriveUnit() {
        return driveUnit;
    }

    public void setDriveUnit(DriveUnit driveUnit) {
        this.driveUnit = driveUnit;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public Wheel getWheel() {
        return wheel;
    }

    public void setWheel(Wheel wheel) {
        this.wheel = wheel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Timestamp getAdvertDate() {
        return advertDate;
    }

    public void setAdvertDate(Timestamp advertDate) {
        this.advertDate = advertDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Advert advert = (Advert) o;
        return id == advert.id
                && engineValue == advert.engineValue
                && cost == advert.cost
                && mileage == advert.mileage
                && Objects.equals(description, advert.description)
                && Objects.equals(releaseDate, advert.releaseDate)
                && Objects.equals(photo, advert.photo)
                && Objects.equals(advertDate, advert.advertDate)
                && Objects.equals(body, advert.body)
                && Objects.equals(color, advert.color)
                && Objects.equals(driveUnit, advert.driveUnit)
                && Objects.equals(engine, advert.engine)
                && Objects.equals(transmission, advert.transmission)
                && Objects.equals(wheel, advert.wheel)
                && Objects.equals(user, advert.user)
                && Objects.equals(model, advert.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
