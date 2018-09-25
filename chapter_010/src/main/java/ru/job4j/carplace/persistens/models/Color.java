package ru.job4j.carplace.persistens.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.job4j.carplace.persistens.interfaces.IEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Описывает цвет автомобиля.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
@Entity
@Table(name = "color")
public class Color implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String color;

    @JsonIgnore
    @OneToMany(mappedBy = "color")
    private List<Advert> adverts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Advert> getAdverts() {
        return adverts;
    }

    public void setAdverts(List<Advert> adverts) {
        this.adverts = adverts;
    }
}
