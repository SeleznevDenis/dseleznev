package ru.job4j.professions;

/**
 * Profession
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Profession {

   String name;
   String profession;

    /**
     * Явно задаем дефолтный конструктор.
     */
   public Profession() {
   }

    /**
     * Конструктор
     * @param name - имя объекта.
     * @param profession - профессия объекта.
     */
   public Profession(String name, String profession) {
       this.name = name;
       this.profession = profession;
   }
    /**
     * getName.
     * @return this.name.
     */
   public String getName() {
       return this.name;
   }

    /**
     * getProfession.
     * @return this.profession.
     */
   public String getProfession() {
       return this.profession;
   }
}
