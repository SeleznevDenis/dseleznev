package ru.job4j.sorting;

/**
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class User implements Comparable{

    private String name;
    private int age;

    /**
     * Конструктор инициализирует поля name и age.
     * @param name имя.
     * @param age возраст.
     */
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }
    public int getAge() {
        return this.age;
    }

    /**
     * Реализованный метод compareTo, интерфейса Comparable,
     * который производит сравнение по полю age, Объектов типа User.
     * @param o объект с которым производится сравнение.
     * @return результат сравнения.
     */
    @Override
    public int compareTo(Object o) {
        User user = (User) o;
        return Integer.compare(this.age, user.age );
    }
}
