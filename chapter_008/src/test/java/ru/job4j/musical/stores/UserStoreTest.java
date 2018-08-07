package ru.job4j.musical.stores;

import org.junit.After;
import org.junit.Test;
import ru.job4j.musical.connectsdb.ConnectPSQL;
import ru.job4j.musical.entities.Address;
import ru.job4j.musical.entities.MusicType;
import ru.job4j.musical.entities.Role;
import ru.job4j.musical.entities.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

import static org.hamcrest.core.Is.is;

/**
 * Test UserStore.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class UserStoreTest {

    private UserStore testStore = new UserStore();
    private MusicType rapMType = new MusicType("rap");
    private MusicType testMType = new MusicType("test");
    private Address address = new Address("address");
    private Role user = new Role("user");
    private Role mod = new Role("moderator");
    private User first = new User("test1", "pass", "test1", asList(rapMType, testMType), address, user);
    private User second = new User("test2", "test", "test2", asList(testMType, new MusicType("jaz")), address, user);
    private User third = new User("test3", "test", "test3", Collections.singletonList(rapMType), address, mod);

    @After
    public void cleanDB() {
        try (Connection con = ConnectPSQL.getInstance().getConnect();
             Statement st = con.createStatement()) {
            st.execute("DELETE FROM address WHERE address = 'address'");
            st.execute("DELETE FROM music_type_users WHERE user_id IN ("
                    + "SELECT id FROM m_users WHERE name IN ('test1', 'test2', 'test3'))");
            st.execute("DELETE FROM m_users WHERE name IN ('test1', 'test2', 'test3')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void add() {
        assertTrue(this.testStore.add(this.first));
        assertTrue(this.testStore.findAll().contains(this.first));
    }

    @Test
    public void findAll() {
        this.testStore.add(this.first);
        this.testStore.add(this.second);
        assertTrue(this.testStore.findAll().containsAll(asList(this.first, this.second)));
    }

    @Test
    public void findById() {
        this.testStore.add(this.first);
        int userId = findIdByName(this.first);
        assertThat(this.testStore.findById(userId), is(this.first));
    }

    private int findIdByName(User user) {
        int result = 0;
        for (User curUser : this.testStore.findAll()) {
            if (curUser.getName().equals(user.getName())) {
                result = curUser.getId();
            }
        }
        return result;
    }

    @Test
    public void findByAddress() {
        this.testStore.add(this.first);
        assertThat(this.testStore.findByAddress(this.address), is(this.first));
    }
    @Test
    public void findByRole() {
        this.testStore.add(this.first);
        this.testStore.add(this.second);
        this.testStore.add(this.third);
        assertTrue(this.testStore.findByRole(this.user).containsAll(asList(this.first, this.second)));
        assertFalse(this.testStore.findByRole(this.user).contains(this.third));
    }

    @Test
    public void findByType() {
        this.testStore.add(this.first);
        this.testStore.add(this.second);
        this.testStore.add(this.third);
        assertTrue(this.testStore.findByType(this.rapMType).containsAll(asList(this.first, this.third)));
        assertFalse(this.testStore.findByType(this.rapMType).contains(this.second));
    }

    @Test
    public void delete() {
        this.testStore.add(this.first);
        int userId = this.findIdByName(this.first);
        this.testStore.delete(userId);
        assertNull(this.testStore.findById(userId));
    }

    @Test
    public void update() {
        this.testStore.add(this.first);
        int userId = this.findIdByName(this.first);
        this.testStore.update(userId, this.second);
        assertThat(this.testStore.findById(userId), is(this.second));
    }
}