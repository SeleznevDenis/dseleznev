package ru.job4j.tracker;

import java.util.List;

/**
 * Интерфейс
 */
public interface Input {
    String ask(String question);

    int ask(String question, List<Integer> ranges);
}
