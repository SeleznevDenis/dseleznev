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
    public int ask(String question, List<Integer> range) {
        int key = Integer.valueOf(this.ask(question));
        boolean exist = false;
        for (int value : range) {
            if (value == key) {
                exist = true;
                break;
            }
        }
        if (exist) {
            return key;
        } else {
            throw new MenuOutException("Out of menu range.");
        }
    }
}
