package ru.job4j.professions;

/**
 * Объект класса Diagnose содержит данные по названию заболевания,
 * длительности болезни, и рекомендуемого лечения.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Diagnose {

    String disease;
    int duration;
    String treatment;

    /**
     * Конструктор класса Diagnose.
     * Инициализирует одноименные поля экземпляра класса.
     * @param disease
     * @param duration
     * @param treatment
     */
    public Diagnose(String disease, int duration, String treatment) {
        this.disease = disease;
        this.duration = duration;
        this.treatment = treatment;

    }
}
