package ru.job4j.tracker;

/**
 * Интерфейс
 */
public interface Input {
    String ask(String question);

    int ask(String question, int[] range);
}
