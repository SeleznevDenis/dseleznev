package ru.job4j.offersparser;

import java.util.Calendar;
import java.util.Objects;

/**
 * Описывает вакансию.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class Vacancy {
    /**
     * Описание вакансиии.
     */
    private String text;

    /**
     * URL вакансии.
     */
    private String url;

    /**
     * Дата размещения вакансии.
     */
    private Calendar date;

    public Vacancy() {
    }

    /**
     * Инициализирует:
     * @param text Описание.
     * @param url Ссылка.
     * @param date Дата размещения.
     */
    public Vacancy(String text, String url, Calendar date) {
        this.text = text;
        this.url = url;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vacancy vacancy = (Vacancy) o;
        return Objects.equals(text, vacancy.text)
                && Objects.equals(url, vacancy.url)
                && Objects.equals(date, vacancy.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, url, date);
    }

    /**
     * Проверяет заполненность вакансии.
     * @return true если все поля заполнены. В ином случае - false.
     */
    public boolean isFull() {
        return this.text != null && this.url != null && this.date != null;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
