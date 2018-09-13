package ru.job4j.annotationmodels;

import ru.job4j.interfaces.IEntity;
import javax.persistence.*;

@Entity
@Table(name = "a_machine")
public class AMachine implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "a_id")
    private int id;

    @Column(name = "a_name")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "a_tramsmission")
    private ATransmission transmission;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "a_engine")
    private AEngine engine;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "a_gear_box")
    private AGearBox gearBox;

    public AMachine(String name, ATransmission transmission, AEngine engine, AGearBox gearBox) {
        this.name = name;
        this.transmission = transmission;
        this.engine = engine;
        this.gearBox = gearBox;
    }

    public AMachine(int id) {
        this.id = id;
    }

    public AMachine() {
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

    public ATransmission getTransmission() {
        return transmission;
    }

    public void setTransmission(ATransmission transmission) {
        this.transmission = transmission;
    }

    public AEngine getEngine() {
        return engine;
    }

    public void setEngine(AEngine engine) {
        this.engine = engine;
    }

    public AGearBox getGearBox() {
        return gearBox;
    }

    public void setGearBox(AGearBox gearBox) {
        this.gearBox = gearBox;
    }
}
