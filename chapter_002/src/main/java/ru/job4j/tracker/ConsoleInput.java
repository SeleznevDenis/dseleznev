package ru.job4j.tracker;

import java.util.*;

/**
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class ConsoleInput implements Input {

    private Scanner scanner = new Scanner(System.in);

    /**
     * Запрашивает у пользователя ввод данных в консоль.
     * @param question запрос.
     * @return возвращает введенные данные.
     */
    public String ask(String question) {
        System.out.print(question);
        return scanner.nextLine();
    }
}
