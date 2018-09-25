package ru.job4j.carplace.persistens.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.job4j.carplace.persistens.interfaces.IEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Описывает марку автомобиля.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
@Entity
@Table(name = "brand")
public class Brand implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "brand")
    private List<Model> models;

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

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }
}
