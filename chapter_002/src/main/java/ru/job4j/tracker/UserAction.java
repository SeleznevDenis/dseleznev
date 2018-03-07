package ru.job4j.tracker;

/**
 * Интерфейс UserAction.
 */
public interface UserAction {

    int key();
    void execute(Input input, Tracker tracker);
    String info();
}
