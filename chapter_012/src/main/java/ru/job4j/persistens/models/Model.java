package ru.job4j.persistens.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.job4j.persistens.interfaces.IEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Model model = (Model) o;
        return Objects.equals(description, model.description)
                && Objects.equals(brand, model.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, brand);
    }
}
