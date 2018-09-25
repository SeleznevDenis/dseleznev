package ru.job4j.annotationstest.annotationmodels;

import javax.persistence.*;

@Entity
@Table(name = "a_gear_box")
public class AGearBox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "a_id")
    private int id;

    @Column(name = "a_desc")
    private String description;

    public AGearBox(String description) {
        this.description = description;
    }

    public AGearBox() {
    }

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
}
