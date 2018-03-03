package ru.job4j.tracker;

import java.util.*;

/**
 * Объект класса Tracker работает с заявками типа Item.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Tracker {

     private Item[] items = new Item[100];
     private int position = 0;
     private static final Random RN = new Random();

    /**
     * Метод реализующий добавление заявки в хранилище.
     * @param item новая заявка.
     * @return
     */
     public Item add(Item item) {
         item.setId(this.generateId());
         this.items[this.position++] = item;
         return item;
     }

    /**
     * Метод заменяет заявку с заданным id в хранилище на заданную заявку.
     * @param id заданный id.
     * @param item заданная заявка.
     */
     public void replace(String id, Item item) {
          for (int i = 0; i <= this.position; i++) {
              if (this.items[i].getId().equals(id)) {
                  this.delete(item.getId());
                  this.items[i] = item;
                  break;
              }
          }

     }

    /**
     * Метод удаляет объект хранящийся в массиве items следующим образом:
     * Метод сдвигает все элементы массива items, на -1 позицию с перезаписью.
     * начиная с объекта массива с индексом id + 1.
     * @param id
     */
     public void delete(String id) {
         for (int i = 0; i <= this.position; i++) {
             if (this.items[i].getId().equals(id)) {
                 for (int j = i; j < this.position; j++) {
                     this.items[j] = this.items[j + 1];
                 }
                 this.items[this.position--] = null;
             }
         }
     }

    /**
     * Возвращает из хранилища массив с заявками.
     * @return массив содержащий ненулевые ссылки на объекты Item.
     */
     public Item[] findAll() {
         Item[] allItem = new Item[this.position];
         int allItemLength = 0;
         for (Item checked : this.items) {
             if (checked != null) {
                 allItem[allItemLength++] = checked;
             }
         }
         return Arrays.copyOf(allItem, allItemLength);
     }

    /**
     * Метод производит поиск заявок в хранилище по имени.
     * @param key заданное имя для поиска.
     * @return массив типа Item содержащий все заявки с совпадающим заданным именем.
     */
     public Item[] findByName(String key) {
         Item[] foundArr = new Item[this.position];
         int foundArrLength = 0;
         for (Item checked : this.items) {
             if (checked != null && checked.getName().equals(key)) {
                 foundArr[foundArrLength++] = checked;
             }
         }
         return Arrays.copyOf(foundArr, foundArrLength);
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
