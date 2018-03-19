package ru.job4j.tracker;

import java.util.List;

/**
 * @author Denis Seleznev(d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class StartUI {

    private final Input input;
    private final Tracker tracker;
    private int[] ranges;

    /**
     * Конструктор инициализирующий поля.
     * @param input
     * @param tracker
     */
    public StartUI(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Основной цикл программы, работает в вечном цикле.
     * Показывает меню и позволяет выбрать пункты,
     * запуская при этом методы соответствующие выбору.
     * Присутствует пункт выхода из вечного цикла.
     */
    public void init() {
        MenuTracker menu = new MenuTracker(this.input, this.tracker);
        menu.fillActions();
        List<Integer> ranges = menu.getNumberMenuItems();
        int key;
        do {
            System.out.println("Menu:");
            menu.show();
            key = this.input.ask("Select: ", ranges);
            menu.select(key);
        } while (key != menu.getExitKey());
    }

    /**
     * Запуск программы.
     * @param args
     */
    public static void main(String[] args) {
        new StartUI(
                new ValidateInput(
                        new ConsoleInput()
                ),
                new Tracker()
        ).init();
    }
}
