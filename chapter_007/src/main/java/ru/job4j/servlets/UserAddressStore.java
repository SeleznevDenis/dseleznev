package ru.job4j.servlets;

import java.util.List;

/**
 * Интерфейс для получения списков городов и стран.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public interface UserAddressStore {
    /**
     * @return все страны.
     */
    public List<String> findAllCountries();

    /**
     * Ищет города соответствующие заданной стране.
     * @param name заданная страна.
     * @return список городов.
     */
    public List<String> findCitiesByCountry(String name);
}
