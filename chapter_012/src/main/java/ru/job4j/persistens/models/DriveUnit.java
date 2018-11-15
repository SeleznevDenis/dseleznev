package ru.job4j.persistens.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.job4j.persistens.interfaces.IEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Описывает коробку передач автомобиля.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
@Entity
@Table(name = "drive_unit")
public class DriveUnit implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "driveUnit")
    private List<Advert> adverts;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DriveUnit driveUnit = (DriveUnit) o;
        return Objects.equals(description, driveUnit.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }
}
