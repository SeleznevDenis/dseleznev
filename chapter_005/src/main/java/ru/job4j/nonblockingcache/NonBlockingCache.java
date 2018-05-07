package ru.job4j.nonblockingcache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple non-blocking cache.
 * @author Denis Seleznev
 * @version $Id$
 * @since 07.05.2018
 */
public class NonBlockingCache {
    /**
     * Кеш.
     */
    private ConcurrentHashMap<Integer, Task> mainStorage = new ConcurrentHashMap<>();

    /**
     * Вводит заявку в кеш.
     * @param task заявка для ввода.
     * @return boolean результат операции.
     */
    public boolean add(Task task) {
        boolean done = false;
        if (task != null) {
            done = (this.mainStorage.putIfAbsent(task.getId(), task) == null);
        }
        return done;
    }

    /**
     * Обновляет заявку в кеше.
     * @param id номер заявки.
     * @param task заявка с новыми параметрами для обновления.
     * @return boolean результат операции.
     */
    public boolean update(Integer id, Task task) {
        Task check = this.mainStorage.computeIfPresent(id, (key, value) -> {
            if (task.getVersion() != value.getVersion()) {
                    throw new OptimisticException();
            }
            task.versionUp();
            return task;
        });
        return check != null;
    }

    /**
     * Удаляет заявку из кеша.
     * @param id номер заявки.
     * @return boolean результат операции.
     */
    public boolean delete(Integer id) {
        return this.mainStorage.remove(id) != null;
    }

    /**
     * Возвращает заявку по id.
     * @param id требуемой заявки.
     * @return Task.
     */
    public Task getById(Integer id) {
        return this.mainStorage.get(id);
    }
}
