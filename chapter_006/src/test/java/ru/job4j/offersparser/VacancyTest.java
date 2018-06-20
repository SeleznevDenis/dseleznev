package ru.job4j.offersparser;

import org.junit.Test;

import java.util.GregorianCalendar;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class VacancyTest {

    @Test
    public void ifAllVacancyFieldIsNotNullThenIsFullShouldReturnTrue() {
        Vacancy testVacancy = new Vacancy();
        testVacancy.setText("test");
        testVacancy.setUrl("test");
        testVacancy.setDate(new GregorianCalendar());
        assertThat(testVacancy.isFull(), is(true));
    }

    @Test
    public void ifVacancyHasNullFieldThenIsFullShouldReturnFalse() {
        Vacancy testVacancy = new Vacancy();
        testVacancy.setDate(new GregorianCalendar());
        assertThat(testVacancy.isFull(), is(false));
    }

    @Test
    public void ifVacancyAreEqualsThenEqualsShouldBeReturnTrue() {
        Vacancy first = new Vacancy("test", "test", new GregorianCalendar(0, 0, 0));
        Vacancy second = new Vacancy("test", "test", new GregorianCalendar(0, 0, 0));
        assertThat(first.equals(second), is(true));
        assertThat(second.equals(first), is(true));
    }

    @Test
    public void ifVacancyDifferenceThenEqualsShouldBeReturnFalse() {
        Vacancy first = new Vacancy("test", "test", new GregorianCalendar(0, 0, 0));
        Vacancy second = new Vacancy("test", "tes", new GregorianCalendar(1, 0, 0));
        assertThat(first.equals(second), is(false));
        assertThat(second.equals(first), is(false));
    }
}