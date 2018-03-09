package ru.job4j.tracker;

import java.util.*;

/**
 * Объект класса EditItem, добавляет новую заявку в трекер.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
class EditItem implements UserAction {
    /**
     * Возвращает заданное число - порядковый номер пункта меню.
     * @return
     */
    public int key() {
        return 2;
    }
    /**
     * Редактирует заявку в хранилище tracker.
     * @param input поток ввода.
     * @param tracker хранилище.
     */
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
    /**
     * Возвращает строку с описание пункта меню.
     * @return
     */
    public String info() {
        return String.format("%s. %s", this.key(), "Edit item");
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
    private UserAction[] actions = new UserAction[7];
    private int ExitKey;
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
     * Инициализирует массив actions.
     */
    public void fillActions() {
       this.actions[0] = new AddItem();
       this.actions[1] = new ShowItems();
       this.actions[2] = new EditItem();
       this.actions[3] = new DeleteItem();
       this.actions[4] = new FindItemById();
       this.actions[5] = new FindItemByName();
       EXIT exit = new EXIT();
       ExitKey = exit.key();
       this.actions[6] = exit;
    }

    /**
     * Возвращает ключ номер пункта выхода из меню.
     * @return
     */
    public int getExitKey() {
        return ExitKey;
    }

    /**
     * Возвращает массив, содержащий ключи номера всех пунктов меню.
     * @return
     */
    public int[] getNumberMenuItems() {
        int[] numberMenuItems = new int[actions.length ];
        int position = 0;
        for (UserAction action : actions) {
            if (action != null) {
                numberMenuItems[position++] = action.key();
            }
        }
        return Arrays.copyOf(numberMenuItems, position);
    }

    /**
     * Позволяет выбрать объект, из массива actions для действия - execute.
     * @param key номер объекта в массиве actions, совпадает с номером пункта меню.
     */
    public void select(int key) {
        this.actions[key].execute(this.input, this.tracker);
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
    private class AddItem implements UserAction {
        /**
         * Возвращает заданное число - порядковый номер пункта меню.
         * @return
         */
        public int key() {
            return 0;
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
        /**
         * Возвращает строку с описание пункта меню.
         * @return
         */
        public String info() {
            return String.format("%s. %s", this.key(), "Add new item");
        }
    }

    /**
     * Выводит в консоль поля всех заявок.
     * @author Denis Seleznev(d.selezneww@mail.ru)
     * @version $Id$
     * @since 0.1
     */
    private static class ShowItems implements UserAction {
        /**
         * Возвращает заданное число - порядковый номер пункта меню.
         * @return
         */
        public int key() {
            return 1;
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
        /**
         * Возвращает строку с описание пункта меню.
         * @return
         */
        public String info() {
            return String.format("%s. %s", this.key(), "Show all items");
        }
    }

    /**
     * Удаляет заявку.
     * @author Denis Seleznev(d.selezneww@mail.ru)
     * @version $Id$
     * @since 0.1
     */
    private class DeleteItem implements UserAction {
        /**
         * Возвращает заданное число - порядковый номер пункта меню.
         * @return
         */
        public int key() {
            return 3;
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
        /**
         * Возвращает строку с описание пункта меню.
         * @return
         */
        public String info() {
            return String.format("%s. %s", this.key(), "Delete item");
        }
    }

    /**
     * Ищет заявку по id.
     * @author Denis Seleznev(d.selezneww@mail.ru)
     * @version $Id$
     * @since 0.1
     */
    private  class FindItemById implements UserAction {
        /**
         * Возвращает заданное число - порядковый номер пункта меню.
         * @return
         */
        public int key() {
            return 4;
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
        /**
         * Возвращает строку с описание пункта меню.
         * @return
         */
        public String info() {
            return String.format("%s. %s", this.key(), "Find item by id");
        }
    }

    /**
     * Ищет заявку по имени.
     * @author Denis Seleznev(d.selezneww@mail.ru)
     * @version $Id$
     * @since 0.1
     */
    private static class FindItemByName implements UserAction {
        /**
         * Возвращает заданное число - порядковый номер пункта меню.
         * @return
         */
        public int key() {
            return 5;
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
        /**
         * Возвращает строку с описание пункта меню.
         * @return
         */
        public String info() {
            return String.format("%s. %s", this.key(), "Find items by name");
        }
    }

    /**
     * Реализует пункт выхода из меню. Позволяет получить ключ для выхода и строку с описанием пункта меню.
     */
    private class EXIT implements UserAction {
        /**
         * Возвращает заданное число - порядковый номер пункта меню.
         * @return
         */
        public int key() {
            return 6;
        }
        /**
         * Метод не используется, сам выход из меню расположен в StartUI.
         * @param input поток ввода.
         * @param tracker хранилище.
         */
        public void execute(Input input, Tracker tracker) {
        }
        /**
         * Возвращает строку с описание пункта меню.
         * @return
         */
        public String info() {
            return String.format("%s. %s", this.key(), "Exit prigram");
        }
    }
}
