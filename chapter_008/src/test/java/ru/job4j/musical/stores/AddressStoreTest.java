package ru.job4j.musical.stores;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.musical.connectsdb.ConnectPSQL;
import ru.job4j.musical.entities.Address;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Test AddressStore.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class AddressStoreTest {

    private final ConnectPSQL connector = ConnectPSQL.getInstance();
    private AddressStore testStore;

    @Before
    public void setUp() throws SQLException {
        this.testStore = new AddressStore();
        try (Connection con = this.connector.getConnect();
             Statement st = con.createStatement()) {
            st.execute("INSERT INTO m_users (id, role_id, password, login) "
                    + "VALUES (-1, 1, 'test', 'test'), (-2, 1, 'test2', 'test2')"
            );
        }
    }

    @After
    public void cleanDB() throws SQLException {
        try (Connection con = this.connector.getConnect();
             Statement st = con.createStatement()) {
            st.execute("DELETE FROM address WHERE id < 0");
            st.execute("DELETE FROM m_users WHERE id < 0");
        }
    }

    @Test
    public void add() {
        assertTrue(this.testStore.add(-1, new Address("test")));
        assertThat(this.testStore.findById(-1).getAddress(), is("test"));
        assertFalse(this.testStore.add(1, null));
    }

    @Test
    public void findAll() {
        this.testStore.add(-1, new Address("test"));
        this.testStore.add(-2, new Address("test2"));
        assertTrue(this.testStore.findAll().containsAll(asList(new Address("test"), new Address("test2"))));
    }

    @Test
    public void findById() {
        this.testStore.add(-1, new Address("test"));
        assertThat(this.testStore.findById(-1), is(new Address("test")));
    }

    @Test
    public void delete() {
        this.testStore.add(-1, new Address("test"));
        assertTrue(this.testStore.delete(-1));
        assertFalse(this.testStore.delete(-1));
        assertNull(this.testStore.findById(-1));
    }

    @Test
    public void update() {
        this.testStore.add(-1, new Address("test"));
        assertTrue(this.testStore.update(-1, new Address("test2")));
        assertFalse(this.testStore.update(-100, new Address("test3")));
        assertFalse(this.testStore.update(-1, null));
        assertThat(this.testStore.findById(-1).getAddress(), is("test2"));
    }
}