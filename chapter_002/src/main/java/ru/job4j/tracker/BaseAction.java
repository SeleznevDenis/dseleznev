package ru.job4j.tracker;

/**
 * Абстрактный класс, реализует методы key и info интерфейса UserAction.
 * Метод execute остается абстрактным.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public abstract class BaseAction implements UserAction {
    private final int key;
    private final String name;

    /**
     * Конструктор.
     * @param key порядковый номер пункта меню.
     * @param name название пункта меню.
     */
    protected BaseAction(final int key, final String name) {
        this.key = key;
        this.name = name;
    }

    /**
     * Возвращает порядковый номер пункта меню.
     * @return
     */
    @Override
    public int key() {
        return this.key;
    }

    /**
     * Возвращает порядковый номер и название пункта меню.
     * @return
     */
    @Override
    public String info() {
        return String.format("%s. %s", this.key, this.name);
    }
}
