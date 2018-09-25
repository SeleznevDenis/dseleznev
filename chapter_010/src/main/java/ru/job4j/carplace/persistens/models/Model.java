package ru.job4j.carplace.persistens.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.job4j.carplace.persistens.interfaces.IEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Описывает модель автомобиля.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
@Entity
@Table(name = "model")
public class Model implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "model")
    private List<Advert> adverts;

    @ManyToOne
    @JoinColumn(name = "brand_fk")
    private Brand brand;

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

    public List<Advert> getAdverts() {
        return adverts;
    }

    public void setAdverts(List<Advert> adverts) {
        this.adverts = adverts;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
