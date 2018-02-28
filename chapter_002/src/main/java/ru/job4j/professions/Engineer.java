package ru.job4j.professions;

/**
 * Engineer.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Engineer extends Profession {
    /**
     * Конструктор класса Engineer.
     * Инициализирует переменные name, и profession экземпляра класса.
     * @param name
     */
    public Engineer(String name) {
        this.name = name;
        this.profession = "Engineer";
    }

    /**
     * build
     * @param house
     * @return строку кто строит и по какому адресу.
     */
    public String build(House house) {
        return this.profession + " " + this.name + " build " + house.address;
    }
}
