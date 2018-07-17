package ru.job4j.servlets;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.Date;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DbStoreTest {

    private final DbStore testStore = DbStore.getInstance();
    private Connection connect;
    private User first = new User("test", "test", "test", "user", "test");
    private User second = new User("test", "test", "test", "user", "test");

    @Before
    public void setUp() {
        Calendar currentDate = new GregorianCalendar();
        currentDate.setTime(new Date());
        this.first.setCreateDate(currentDate);
        this.second.setCreateDate(currentDate);
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("queries.properties")) {
            Properties prop = new Properties();
            prop.load(stream);
            this.connect = DriverManager.getConnection(
                    prop.getProperty("url"),
                    prop.getProperty("user"),
                    prop.getProperty("password")
            );
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void clean() throws SQLException {
        try (Statement st = this.connect.createStatement()) {
            st.execute("DELETE FROM users WHERE login = 'test'");
        } finally {
            if (this.connect != null) {
                this.connect.close();
            }
        }
    }



    @Test
    public void getInstance() {
        assertThat(this.testStore, is(DbStore.getInstance()));
    }

    @Test
    public void ifFindByLoginThenReturnUserWithSameLogin() {
        this.testStore.add(this.first);
        String result = this.testStore.findByLogin(this.first.getLogin()).getName();
        assertThat(result, is(this.first.getName()));
    }

    @Test
    public void add() throws SQLException {
        this.testStore.add(this.first);
        try (Statement st = this.connect.createStatement();
             ResultSet rstSet = st.executeQuery("SELECT * FROM users WHERE name = 'test'")) {
            rstSet.next();
            assertThat(rstSet.getString("name"), is("test"));
            assertThat(rstSet.getString("login"), is("test"));
            assertThat(rstSet.getString("email"), is("test"));
            assertThat(rstSet.getInt("id") > 0, is(true));
            assertThat(rstSet.next(), is(false));
        }
    }

    @Test
    public void update() throws SQLException {
        int userId = 0;
        try (Statement st = this.connect.createStatement()) {
            st.execute("INSERT INTO users (name, login, email) VALUES ('up', 'up', 'up')");
            try (ResultSet rstSet = st.executeQuery("SELECT id FROM users WHERE name = 'up'")) {
                rstSet.next();
                userId = rstSet.getInt("id");
            }
            User userUpdated = new User("test", "test", "test", "user", "test");
            userUpdated.setId(userId);
            this.testStore.update(userUpdated);
            try (ResultSet rstSet = st.executeQuery("SELECT name, login, email FROM users WHERE id =" + userId)) {
                assertThat(rstSet.next(), is(true));
                assertThat(rstSet.getString("name"), is("test"));
                assertThat(rstSet.getString("login"), is("test"));
                assertThat(rstSet.getString("email"), is("test"));
                assertThat(rstSet.next(), is(false));
            }
        }
    }

    @Test
    public void delete() throws SQLException {
        int userId = 0;
        try (Statement st = this.connect.createStatement()) {
            st.execute("INSERT INTO users (name, login, email) VALUES ('test', 'test', 'test')");
            try (ResultSet rstSet = st.executeQuery("SELECT id FROM users WHERE name = 'test'")) {
                rstSet.next();
                userId = rstSet.getInt("id");
            }
            this.testStore.delete(userId);
            try (ResultSet rstSet = st.executeQuery("SELECT * FROM users WHERE name = 'test'")) {
                assertThat(rstSet.next(), is(false));
            }
        }
    }

    @Test
    public void findAll() {
        this.testStore.add(first);
        this.testStore.add(second);
        try (Statement st = this.connect.createStatement();
             ResultSet rstSet = st.executeQuery("SELECT id FROM users WHERE name = 'test'")) {
            rstSet.next();
            this.first.setId(rstSet.getInt("id"));
            rstSet.next();
            this.second.setId(rstSet.getInt("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<User> result = this.testStore.findAll();
        List<User> expect = new ArrayList<>(asList(first, second));
        assertThat(result.containsAll(expect), is(true));
    }

    @Test
    public void findById() {
        this.testStore.add(first);
        try (Statement st = this.connect.createStatement();
             ResultSet rstSet = st.executeQuery("SELECT id FROM users WHERE name = 'test'")) {
            rstSet.next();
            this.first.setId(rstSet.getInt("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertThat(this.testStore.findById(this.first.getId()), is(this.first));
    }

    @Test
    public void findAllCountriesTest() {
        assertTrue(this.testStore.findAllCountries().containsAll(asList("Russia", "USA")));
    }

    @Test
    public void findCitiesByCountry() {
        assertTrue(this.testStore.findCitiesByCountry("Russia").containsAll(
                asList("Moscow", "Saint Petersburg", "Ekaterinburg"))
        );
    }
}