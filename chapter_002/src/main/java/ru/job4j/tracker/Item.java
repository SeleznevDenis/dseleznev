package ru.job4j.tracker;

/**
 * Объект класса Item хранит заявку с полями:
 * id - номер заявки, name - имя, desc - описание,
 * created - время создания, comments массив комментариев.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Item {

    private String id;
    private String name;
    private String desc;
    private long created;
    private String[] comments;

    public Item() {
    }

    public Item(String name, String desc, long created) {
        this.name = name;
        this.desc = desc;
        this.created = created;
    }

    public String getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getDesc() {
        return this.desc;
    }
    public long getCreated() {
        return this.created;
    }
    public String[] getComments() {
        return this.comments;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public void setCreated(long created) {
        this.created = created;
    }
    public void setComments(String[] comments) {
        this.comments = comments;
    }
}
