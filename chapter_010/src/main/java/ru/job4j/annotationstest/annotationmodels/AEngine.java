package ru.job4j.annotationstest.annotationmodels;

import javax.persistence.*;

@Entity
@Table(name = "a_engine")
public class AEngine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "a_id")
    private int id;

    @Column(name = "a_description")
    private String description;

    public AEngine(String description) {
        this.description = description;
    }

    public AEngine() {
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
