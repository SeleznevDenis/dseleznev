package ru.job4j.tracker;

import java.util.*;

/**
 * @author Denis Seleznev(d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class StartUI {

    private static final String ADD = "0";
    private static final String SHOW = "1";
    private static final String EDIT = "2";
    private static final String DELETE = "3";
    private static final String FINDBYID = "4";
    private static final String FINDBYNAME = "5";
    private static final String EXIT = "6";

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
        boolean exit = false;
        while (!exit) {
            this.showMenu();
            String answer = this.input.ask("Select: ");
            if (ADD.equals(answer)) {
                this.createItem();
            } else if (SHOW.equals(answer)) {
                this.showAllItems();
            } else if (EDIT.equals(answer)) {
                this.editItem();
            } else if (DELETE.equals(answer)) {
                this.deleteItem();
            } else if (FINDBYID.equals(answer)) {
                this.findById();
            } else if (FINDBYNAME.equals(answer)) {
                this.findByName();
            } else if (EXIT.equals(answer)) {
                exit = true;
            } else  {
                System.out.println("Incorrect value, please try again");
            }
        }
    }

    /**
     * Создает новую заявку и помещает в хранилище.
     * Поля имя и описание запрашивает у пользователя, а
     * id и время создания задает сам.
     */
    private void createItem() {
        System.out.println("------------- Adding a new item -------------");
        String name = this.input.ask("Enter the item name : ");
        String desc = this.input.ask("Enter the item description : ");
        long ItemDate = new Date().getTime();
        Item item = new Item(name, desc, ItemDate);
        this.tracker.add(item);
        this.showItem(item);
        System.out.println("---------------- End adding -----------------");
    }

    /**
     * Выводит в консоль все заявки из хранилища со значениями их полей.
     */
    private void showAllItems() {
        System.out.println("---------------- All Items ------------------");
        for (Item show : this.tracker.findAll()) {
            this.showItem(show);
        }
        System.out.println("------------------- End ---------------------");

    }

    /**
     * Предлагает выбрать заявку для редактирования по id и показывает её поля,
     * затем уточняет, её ли редактировать. Если ответ пользователя "Y",
     * то запрашивает новое имя для заявки, затем новое описание, затем
     * создает новую заявку с измененным именем и описанием.
     * Сохраняет id редактируемой заявки прежним, обновляет время создания
     * заявки на текущее.
     *
     */
    private void editItem() {
        System.out.println("---------------- Edit item ------------------");
        String selectedId = selectAndShowItem();
        String done = this.input.ask("Edit this item? Y/any key : ");
        if (done.equals("Y")) {
            String newName = this.input.ask("Enter new item's name : ");
            String newDesc = this.input.ask("Enter new description : ");
            Item newItem = new Item(newName, newDesc, new Date().getTime());
            newItem.setId(selectedId);
            this.tracker.replace(selectedId, newItem);
            System.out.println("The item was changed");
        }
        System.out.println("----------------Edit end---------------------");
    }

    /**
     * Удаляет заявку по указанному id.
     */
    private void deleteItem() {
        System.out.println("---------------- Deleting item --------------");
        String selectedId = selectAndShowItem();
        String done = this.input.ask("Delete this item? Y/any key : ");
        if (done.equals("Y")) {
            this.tracker.delete(selectedId);
            System.out.println("Item was deleted");
        }
        System.out.println("-------------- End of deleting --------------");
    }

    /**
     * Ищет в хранилище заявку по id и выводит значения её полей в консоль.
     * Далее спрашивает, показать ли ещё заявку, и если "Y" то, повторяет предыдущие действия.
     */
    private void findById() {
        System.out.println("---------------- Show item ------------------");
        String done;
        do {
            selectAndShowItem();
            done = this.input.ask("Show another item? Y/any key : ");
        } while (done.equals("Y"));
        System.out.println("---------------- Show item end --------------");
    }

    /**
     * Ищет в хранилище все заявки по имени и выводит значения их полей в консоль.
     */
    private void findByName() {
        System.out.println("----------------Find items by name-----------");
        String selectedName = this.input.ask("Enter item's name : ");
        for (Item show : this.tracker.findByName(selectedName)) {
            this.showItem(show);
        }
        System.out.println("------------------- End ---------------------");
    }

    /**
     * Запрашивает id заявки, выводит в консоль поля заявки.
     * @return id заявки.
     */
    private String selectAndShowItem() {
        String selectedId = this.input.ask("Enter item's id : ");
        Item selected = tracker.findById(selectedId);
        System.out.println("Selected item : ");
        this.showItem(selected);
        return selectedId;
    }

    /**
     * Показывает значение полей заявки.
     * @param item заявка.
     */
    private void showItem(Item item) {
        if (item != null) {
            System.out.println(item.getId() + " | " + item.getName() + " | " + new Date(item.getCreated()));
            System.out.println(item.getDesc());
        }
    }

    /**
     * Выводит в консоль все пункты меню.
     */
    private void showMenu() {
        System.out.println("Menu:");
        System.out.println("0. Add new item");
        System.out.println("1. Show all items");
        System.out.println("2. Edit item");
        System.out.println("3. Delete item");
        System.out.println("4. Find item by id");
        System.out.println("5. Find items by name");
        System.out.println("6. Exit prigram");
    }

    /**
     * Запуск программы.
     * @param args
     */
    public static void main(String[] args) {
        new StartUI(new ConsoleInput(), new Tracker()).init();
    }
}
