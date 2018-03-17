package ru.job4j.search;

/**
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Task {
    private String desc;
    private int priority;

    /**
     * Конструктор, инициализирует поля описание и приоритет.
     * @param desc описание.
     * @param priority приоритет.
     */
    public Task(String desc, int priority) {
        this.desc = desc;
        this.priority = priority;
    }

    public String getDesc() {
        return desc;
    }

    public int getPriority() {
        return priority;
    }
}
