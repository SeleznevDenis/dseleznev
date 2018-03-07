package ru.job4j.tracker;

/**
 * @author Denis Seleznev(d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class StartUI {

    private static final int EXIT = 6;
    private final Input input;
    private final Tracker tracker;

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
        int key;
        do {
            System.out.println("Menu:");
            menu.show();
            System.out.println("6. Exit prigram");
            key = Integer.valueOf(this.input.ask("Select: "));
            if (key != EXIT) {
                menu.select(key);
            }
        } while (key != EXIT);
    }

    /**
     * Запуск программы.
     * @param args
     */
    public static void main(String[] args) {
        new StartUI(new ConsoleInput(), new Tracker()).init();
    }
}
