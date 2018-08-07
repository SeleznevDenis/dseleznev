package ru.job4j.musical.entities;

import java.util.Objects;

/**
 * Сущность музыкальный тип.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class MusicType {

    private String type;
    private int id;

    public MusicType() {
    }

    public MusicType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MusicType musicType = (MusicType) o;
        return Objects.equals(type, musicType.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
