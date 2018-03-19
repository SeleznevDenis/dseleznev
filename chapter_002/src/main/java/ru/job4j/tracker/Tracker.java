package ru.job4j.tracker;

import java.util.*;

/**
 * Объект класса Tracker работает с заявками типа Item.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Tracker {

    private ArrayList<Item> items = new ArrayList<>();
    private static final Random RN = new Random();

    /**
     * Метод реализующий добавление заявки в хранилище.
     * @param item новая заявка.
     * @return
     */
     public Item add(Item item) {
         item.setId(this.generateId());
         items.add(item);
         return item;
     }

    /**
     * Метод заменяет заявку с заданным id в хранилище на заданную заявку.
     * @param id заданный id.
     * @param item заданная заявка.
     */
     public void replace(String id, Item item) {
         for (Item checkedItem : this.items) {
             if (checkedItem.getId().equals(id)) {
                 this.items.set(this.items.indexOf(checkedItem), item);
                 break;
             }
         }
     }

    /**
     * Метод удаляет объект хранящийся в ArrayList items.
     * @param id
     */
     public void delete(String id) {
         for (Item checkedItem : this.items) {
             if (checkedItem.getId().equals(id)) {
                 this.items.remove(checkedItem);
                 break;
             }
         }
     }

    /**
     * Возвращает из хранилища ArrayList с заявками.
     * @return массив содержащий ненулевые ссылки на объекты Item.
     */
     public ArrayList<Item> findAll() {
        return this.items;
     }

    /**
     * Метод производит поиск заявок в хранилище по имени.
     * @param key заданное имя для поиска.
     * @return ArrayList содержащий все заявки с совпадающим заданным именем.
     */
     public ArrayList<Item> findByName(String key) {
         ArrayList<Item> foundArr = new ArrayList<>();
         for (Item checkedItem : this.items) {
             if (checkedItem.getName().equals(key)) {
                 foundArr.add(checkedItem);
             }
         }
         return foundArr;
     }

    /**
     * findById ищет заявку в хранилище по id.
     * @param id заданный для поиска id.
     * @return найденная заявка.
     */
     public Item findById(String id) {
         Item found = null;
         for (Item checked : this.items) {
             if (checked != null && checked.getId().equals(id)) {
                 found = checked;
                 break;
             }
         }
         return found;
     }

    /**
     * Метод генерирует уникальный ключ для заявки.
     * @return Уникальный ключ.
     */
     private String generateId() {
         return String.valueOf(System.currentTimeMillis() + RN.nextInt());
     }
}
