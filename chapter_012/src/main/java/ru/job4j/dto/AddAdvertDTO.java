package ru.job4j.dto;

import ru.job4j.persistens.models.Advert;

/**
 * Описывает объект, для передачи с фронта объявления, и требуемого действия с ним.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class AddAdvertDTO {
    private Advert advert;
    private String action;

    public Advert getAdvert() {
        return advert;
    }

    public void setAdvert(Advert advert) {
        this.advert = advert;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
