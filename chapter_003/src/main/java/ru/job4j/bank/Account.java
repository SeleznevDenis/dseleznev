package ru.job4j.bank;

/**
 * Описывает аккаунт пользователя.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Account {

    private double value;
    private String requisites;

    /**
     * Конструктор инициализирует поле requisites.
     * Также инициализирует поле value нулем.
     * @param requisites реквизиты.
     */
    public Account(String requisites) {
        this.requisites = requisites;
    }

    public double getValue() {
        return value;
    }
    public String getRequisites() {
        return requisites;
    }

    /**
     * Увеличивает value на величину переданную в параметре
     * @param value количество денег на счете.
     */
    public void changeValue(double value) {
        this.value += value;
    }
}
