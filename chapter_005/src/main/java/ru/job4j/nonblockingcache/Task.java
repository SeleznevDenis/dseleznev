package ru.job4j.nonblockingcache;

/**
 * Модель для работы с неблокирующим кешем,
 * Содержит поля version и name.
 * @author Denis Seleznev
 * @version $Id$
 * @since 06.05.2018
 */
public class Task {
    private int version;
    private int id;
    private String name;

    public Task(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void versionUp() {
        this.version++;
    }

    public String getName() {
        return this.name;
    }

    public int getVersion() {
        return this.version;
    }

    public int getId() {
        return this.id;
    }
}

