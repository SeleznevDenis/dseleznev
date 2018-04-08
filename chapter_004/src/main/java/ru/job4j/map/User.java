package ru.job4j.map;

import java.util.Calendar;

public class User {

    private String name;
    private int children;
    private Calendar birthday;

    public User(String name, int children, Calendar birthday) {
        this.name = name;
        this.children = children;
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return this.children == user.children
                && this.name.equals(user.name)
                && this.birthday.equals(user.birthday);
    }


    @Override
    public int hashCode() {
        int result = 17;
        int prime = 31;
        result = prime * result * name.hashCode();
        result = prime * result * children;
        result = prime * result * birthday.hashCode();
        return result;
    }
}

