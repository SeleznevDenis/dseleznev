package ru.job4j.offersparser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test SQLStore.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class SQLStoreTest {
    /**
     * Хранит ссылку на коннект для целей тестирования.
     */
    private Connection testConnect;

    /**
     * Подключается к базе данных перед тестом.
     */
    @Before
    public void connectDb() throws SQLException, IOException {
        Properties properties = new Properties();
        try (InputStream reader = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(reader);
        }
        this.testConnect = DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("username"),
                properties.getProperty("password")
        );
    }

    /**
     * После выполнения теста удаляет из базы данных вакансию содержащую
     * описание "test". Закрывает текущее подключение к базе данных
     */
    @After
    public void cleanDB() throws SQLException {
        this.testConnect.createStatement().execute("DELETE FROM vacancy WHERE description = 'test'");
        this.testConnect.close();

    }

    @Test
    public void ifInsertVacancyThenDBHasThisVacancy() throws SQLException {
        SQLStore testStore = new SQLStore();
        testStore.init();
        Vacancy testVacancy = new Vacancy("test", "test", new GregorianCalendar(2999, 3, 3));
        Set<Vacancy> testSet = new HashSet<>();
        testSet.add(testVacancy);
        testStore.insertVacancies(testSet);
        ResultSet result = this.testConnect.createStatement().executeQuery(
                "SELECT v.link, v.description, v.date FROM vacancy AS v WHERE description = 'test'"
        );
        result.next();
        String resultName = result.getString("link");
        String resultDesc = result.getString("description");
        long resultTime = result.getTimestamp("date").getTime();
        Calendar resultCalendar = new GregorianCalendar();
        resultCalendar.setTimeInMillis(resultTime);
        assertThat(resultName, is("test"));
        assertThat(resultDesc, is("test"));
        assertThat(resultCalendar, is(new GregorianCalendar(2999, 3, 3)));
    }

    @Test
    public void getLastDateShouldBeReturnVacancyWithLastDate() throws SQLException {
        this.testConnect.createStatement().execute(
                "INSERT INTO vacancy (link, description, date) VALUES ('test', 'test', '2999-05-29 00:00:00')"
        );
        SQLStore testStore = new SQLStore();
        testStore.init();
        Calendar result = testStore.getLastDate();
        Calendar expect = new GregorianCalendar(2999, 4, 29);
        assertThat(result, is(expect));
    }
}