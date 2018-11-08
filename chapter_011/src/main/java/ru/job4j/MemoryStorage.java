package ru.job4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Memory Storage.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class MemoryStorage implements Storage {

    private AtomicInteger counter = new AtomicInteger();
    private Map<Integer, User> storage = new HashMap<>();

    /**
     * Добавляет объект в хранилище.
     * @param user объект для добавления.
     */
    @Override
    public int add(User user) {
        System.out.println("memory add");
        int userId = this.counter.incrementAndGet();
        user.setId(userId);
        storage.put(userId, user);
        return userId;
    }

    /**
     * Ищет объект в хранилище.
     * @param id идентификатор искомого объекта.
     * @return искомый объект.
     */
    @Override
    public User findById(int id) {
        return this.storage.get(id);
    }
}
