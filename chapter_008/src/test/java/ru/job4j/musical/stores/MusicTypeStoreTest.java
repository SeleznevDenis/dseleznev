package ru.job4j.musical.stores;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.musical.connectsdb.ConnectPSQL;
import ru.job4j.musical.entities.MusicType;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Test MusicTypeStore.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class MusicTypeStoreTest {

    private MusicTypeStore testStore;

    @Before
    public void setUp() {
        this.testStore = new MusicTypeStore();
    }

    @After
    public void cleanDB() throws SQLException {
        try (Connection con = ConnectPSQL.getInstance().getConnect();
             Statement st = con.createStatement()) {
            st.execute("DELETE FROM music_type WHERE music_type IN ('test6', 'test7')");
        }
    }

    @Test
    public void add() {
        this.testStore.add(new MusicType("test6"));
        assertTrue(this.testStore.findAll().contains(new MusicType("test6")));
    }

    @Test
    public void findAll() {
        this.testStore.add(new MusicType("test6"));
        this.testStore.add(new MusicType("test7"));
        assertTrue(this.testStore.findAll().containsAll(asList(new MusicType("test6"), new MusicType("test7"))));
    }

    @Test
    public void findById() {
        this.testStore.add(new MusicType("test6"));
        int id = this.findIdByType(new MusicType("test6"));
        assertThat(this.testStore.findById(id), is(new MusicType("test6")));
        assertNull(this.testStore.findById(0));
    }

    private int findIdByType(MusicType type) {
        int result = 0;
        for (MusicType mt : this.testStore.findAll()) {
            if (mt.getType().equals(type.getType())) {
                result = mt.getId();
            }
        }
        return result;
    }

    @Test
    public void delete() {
        this.testStore.add(new MusicType("test6"));
        int id = this.findIdByType(new MusicType("test6"));
        assertTrue(this.testStore.delete(id));
        assertFalse(this.testStore.delete(id));
    }

    @Test
    public void update() {
        this.testStore.add(new MusicType("test6"));
        int id = this.findIdByType(new MusicType("test6"));
        assertTrue(this.testStore.update(id, new MusicType("test7")));
        assertFalse(this.testStore.update(id, null));
        assertFalse(this.testStore.update(0, new MusicType("test6")));
        assertThat(this.testStore.findById(id).getType(), is("test7"));
    }
}