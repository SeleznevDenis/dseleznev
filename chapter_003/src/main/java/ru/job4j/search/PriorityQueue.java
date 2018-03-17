package ru.job4j.search;

import java.util.LinkedList;

/**
 * Очередь с приоритетом.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class PriorityQueue {
    private LinkedList<Task> tasks = new LinkedList<>();

    /**
     * Добавляет объект в список tasks, при этом
     * объекты с более низким приоритетом располагаются в
     * списке ближе к началу.
     * @param task объект добавляемый в список.
     */
    public void put(Task task) {
        if (this.tasks.size() != 0) {
            for (int i = 0; i < tasks.size(); i++) {
                if (task.getPriority() <= tasks.get(i).getPriority()) {
                    tasks.add(i, task);
                    break;
                }
            }
        } else {
            this.tasks.add(task);
        }
    }

    /**
     * Возвращает объект из головы очереди tasks и удаляет его.
     * @return
     */
    public Task take() {
        return this.tasks.poll();
    }
}
