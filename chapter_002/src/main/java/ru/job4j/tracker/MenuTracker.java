package ru.job4j.tracker;

import java.util.*;

/**
 * Объект класса EditItem, добавляет новую заявку в трекер.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
class EditItem extends BaseAction {

    public EditItem(int key, String name) {
        super(key, name);
    }

    /**
     * Редактирует заявку в хранилище tracker.
     * @param input поток ввода.
     * @param tracker хранилище.
     */
    @Override
    public void execute(Input input, Tracker tracker) {
        System.out.println("---------------- Edit item ------------------");
        String selectedId = new MenuTracker(input, tracker).selectAndShowItem();
        String done = input.ask("Edit this item? Y/any key : ");
        if (done.equals("Y")) {
            String name = input.ask("Enter the new item name : ");
            String desc = input.ask("Enter the new item description : ");
            Item item = new Item(name, desc, new Date().getTime());
            item.setId(selectedId);
            tracker.replace(selectedId, item);
            System.out.println("The item was changed");
        }
        System.out.println("----------------Edit end---------------------");
    }
}

/**
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class MenuTracker {

    private Input input;
    private Tracker tracker;
    private ArrayList<UserAction> actions = new ArrayList<>();
    private int exitKey;
    /**
     * Объект класса MenuTracker реализует меню.
     * Конструктор класса MenuTracker инициализирует поля input и tracker
     * @param input поток ввода.
     * @param tracker хранилище заявок.
     */
    public MenuTracker(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Инициализирует ArrayList actions.
     */
    public void fillActions() {
        this.actions.add(new AddItem(0, "Add new item"));
        this.actions.add(new ShowItems(1, "Show all items"));
        this.actions.add(new EditItem(2, "Edit item"));
        this.actions.add(new DeleteItem(3, "Delete item"));
        this.actions.add(new FindItemById(4, "Find item by id"));
        this.actions.add(new FindItemByName(5, "Find items by name"));
        EXIT exit = new EXIT(6, "Exit prigram");
        exitKey = exit.key();
        this.actions.add(exit);
    }

    /**
     * Возвращает ключ номер пункта выхода из меню.
     * @return
     */
    public int getExitKey() {
        return exitKey;
    }

    /**
     * Возвращает ArrayList, содержащий ключи номера всех пунктов меню.
     * @return
     */
    public ArrayList<Integer> getNumberMenuItems() {
        ArrayList<Integer> numberMenuItems = new ArrayList<>();
        for (UserAction action : actions) {
            numberMenuItems.add(action.key());
        }
        return numberMenuItems;
    }

    /**
     * Позволяет выбрать объект, из ArrayList actions для действия - execute.
     * @param key номер объекта в массиве actions, совпадает с номером пункта меню.
     */
    public void select(int key) {
        this.actions.get(key).execute(this.input, this.tracker);
    }

    /**
     * Выводит в консоль список пунктов меню.
     */
    public void show() {
        for (UserAction action : this.actions) {
            if (action != null) {
                System.out.println(action.info());
            }
        }
    }

    /**
     * Выводит в консоль все поля объекта Item.
     * @param item
     */
    private void showItem(Item item) {
        if (item != null) {
            System.out.println(
                    String.format("%s | %s | %s%n%s",
                            item.getId(), item.getName(), new Date(item.getCreated()), item.getDesc()
                    )
            );
        }
    }

    /**
     * Запрашивает id и выводит в консоль все поля объекта Item из хранилища tracker  с данным id.
     * @return
     */
    String selectAndShowItem() {
         String selectedId = this.input.ask("Enter item's id : ");
         Item selected = this.tracker.findById(selectedId);
         System.out.println("Selected item : ");
         showItem(selected);
         return selectedId;
    }

    /**
     * Добавляет новую заявку в хранилище.
     * @author Denis Seleznev(d.selezneww@mail.ru)
     * @version $Id$
     * @since 0.1
     */
    private class AddItem extends BaseAction {
        /**
         * Конструктор
         * @param key инициализирует порядковый номер пункта меню.
         * @param name инициализирует название пукта меню.
         */
        public AddItem(int key, String name) {
            super(key, name);
        }

        /**
         * Добавляет новую заявку в хранилище tracker.
         * @param input поток ввода.
         * @param tracker хранилище.
         */
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------- Adding a new item -------------");
            String name = input.ask("Enter the item name : ");
            String desc = input.ask("Enter the item description : ");
            Item item = tracker.add(new Item(name, desc, new Date().getTime()));
            showItem(item);
            System.out.println("---------------- End adding -----------------");
        }
    }

    /**
     * Выводит в консоль поля всех заявок.
     * @author Denis Seleznev(d.selezneww@mail.ru)
     * @version $Id$
     * @since 0.1
     */
    private static class ShowItems extends BaseAction {
        /**
         * Конструктор
         * @param key инициализирует порядковый номер пункта меню.
         * @param name инициализирует название пукта меню.
         */
        public ShowItems(int key, String name) {
            super(key, name);
        }
        /**
         * Выводит в консоль поля всех заявок в хранилище tracker.
         * @param input поток ввода.
         * @param tracker хранилище.
         */
        public void execute(Input input, Tracker tracker) {
            System.out.println("---------------- All Items ------------------");
            for (Item item : tracker.findAll()) {
                new MenuTracker(input, tracker).showItem(item);
            }
            System.out.println("------------------- End ---------------------");
        }
    }

    /**
     * Удаляет заявку.
     * @author Denis Seleznev(d.selezneww@mail.ru)
     * @version $Id$
     * @since 0.1
     */
    private class DeleteItem extends BaseAction {
        /**
         * Конструктор
         * @param key инициализирует порядковый номер пункта меню.
         * @param name инициализирует название пукта меню.
         */
        public DeleteItem(int key, String name) {
            super(key, name);
        }
        /**
         * Удаляет заявку из хранилища tracker.
         * @param input поток ввода.
         * @param tracker хранилище.
         */
        public void execute(Input input, Tracker tracker) {
            System.out.println("---------------- Deleting item --------------");
            String selectedId = selectAndShowItem();
            String done = input.ask("Delete this item? Y/any key : ");
            if (done.equals("Y")) {
                tracker.delete(selectedId);
                System.out.println("Item was deleted");
            }
            System.out.println("-------------- End of deleting --------------");
        }
    }

    /**
     * Ищет заявку по id.
     * @author Denis Seleznev(d.selezneww@mail.ru)
     * @version $Id$
     * @since 0.1
     */
    private  class FindItemById extends BaseAction {
        /**
         * Конструктор
         * @param key инициализирует порядковый номер пункта меню.
         * @param name инициализирует название пукта меню.
         */
        public FindItemById(int key, String name) {
            super(key, name);
        }
        /**
         * Ищет по id и выводит в консоль заявку в хранилище tracker.
         * @param input поток ввода.
         * @param tracker хранилище.
         */
        public void execute(Input input, Tracker tracker) {
            System.out.println("---------------- Show item ------------------");
            String done;
            do {
                selectAndShowItem();
                done = input.ask("Show another item? Y/any key : ");
            } while (done.equals("Y"));
            System.out.println("---------------- Show item end --------------");
        }
    }

    /**
     * Ищет заявку по имени.
     * @author Denis Seleznev(d.selezneww@mail.ru)
     * @version $Id$
     * @since 0.1
     */
    private static class FindItemByName extends BaseAction {
        /**
         * Конструктор
         * @param key инициализирует порядковый номер пункта меню.
         * @param name инициализирует название пукта меню.
         */
        public FindItemByName(int key, String name) {
            super(key, name);
        }

        /**
         * Ищет по имени и выводит в консоль заявку из хранилища tracker.
         * @param input поток ввода.
         * @param tracker хранилище.
         */
        public void execute(Input input, Tracker tracker) {
            System.out.println("----------------Find items by name-----------");
            String selectedName = input.ask("Enter item's name : ");
            for (Item show : tracker.findByName(selectedName)) {
                 new MenuTracker(input, tracker).showItem(show);
            }
            System.out.println("------------------- End ---------------------");
        }
    }

    /**
     * Реализует пункт выхода из меню. Позволяет получить ключ для выхода и строку с описанием пункта меню.
     */
    private class EXIT extends BaseAction {
        /**
         * Конструктор
         * @param key инициализирует порядковый номер пункта меню.
         * @param name инициализирует название пукта меню.
         */
        public EXIT(int key, String name) {
            super(key, name);
        }

        /**
         * Метод не используется, сам выход из меню расположен в StartUI.
         * @param input
         * @param tracker
         */
        public void execute(Input input, Tracker tracker) {
        }
    }
}
