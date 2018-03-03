package ru.job4j.professions;

/**
 * Teacher.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Teacher extends Profession {

    /**
     * Конструктор класса Teacher.
     * Инициализирует поля name и profession экземпляра класса.
     * @param name
     */
    public Teacher(String name) {
        super(name, "Teacher");
    }

    /**
     * teach
     * @param student
     * @return строку кто учит и кого.
     */
    public String teach(Student student) {
        return this.profession + " " + this.name + " teach student " + student.name;
    }
}
