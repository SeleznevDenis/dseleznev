package ru.job4j.search;

import java.util.ArrayList;
import java.util.List;

/**
 * Объект класса имитирует телефонный справочник.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class PhoneDictionary {
    private List<Person> persons = new ArrayList<Person>();

    /**
     * Добавляет объект в справочник.
     * @param person добавляемый объект.
     */
    public void add(Person person) {
        this.persons.add(person);
    }

    /**
     * Проводит поиск в справочнике.
     * @param key Строка которая содержится в любом из полей искомого объекта.
     * @return ArrayList с найденными объектами.
     */
    public List<Person> find(String key) {
        List<Person> result = new ArrayList<>();
        for (Person check : persons) {
            if (check.getAddress().contains(key) || check.getName().contains(key)
                    || check.getPhone().contains(key) || check.getSurname().contains(key)) {
                result.add(check);
            }
        }
        return result;
    }
}
