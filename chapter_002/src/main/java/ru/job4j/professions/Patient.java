package ru.job4j.professions;

/**
 * Patient.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Patient {

    String name;
    Diagnose diagnose;

    /**
     * Конструктор класса Patient.
     * Инициализирует переменную name экземпляра класса.
     * @param name
     */
    public Patient(String name) {
        this.name = name;
    }
}
