package ru.job4j.search;

/**
 * Имитирует карточку с данными объекта, содержащимся в телефонном справочнике.
 * @author Denis Seleznev(d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Person {
    private String name;
    private String surname;
    private String phone;
    private String address;

    /**
     * Конструктор, инициализирует поля имя, фамилия, телефон, адрес.
     * @param name имя.
     * @param surname фимилия.
     * @param phone телефон.
     * @param address адрес.
     */
    public Person(String name, String surname, String phone, String address) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}
