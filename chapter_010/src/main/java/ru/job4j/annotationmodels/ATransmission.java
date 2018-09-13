package ru.job4j.annotationmodels;

import javax.persistence.*;

@Entity
@Table(name = "a_transmission")
public class ATransmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "a_id")
    private int id;

    @Column(name = "a_desc")
    private String description;

    public ATransmission(String description) {
        this.description = description;
    }

    public ATransmission() {
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
