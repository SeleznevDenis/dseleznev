package ru.job4j.dto;

import ru.job4j.persistens.models.Brand;

import java.sql.Timestamp;

/**
 * Переносит данные фильтрации объявлений.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class FilterDTO {
    private Timestamp startDate;
    private Timestamp finishDate;
    private Brand brand;
    private boolean withPhoto;

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Timestamp finishDate) {
        this.finishDate = finishDate;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public boolean isWithPhoto() {
        return withPhoto;
    }

    public void setWithPhoto(boolean withPhoto) {
        this.withPhoto = withPhoto;
    }
}
