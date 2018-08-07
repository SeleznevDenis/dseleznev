package ru.job4j.musical.stores;

import org.junit.After;
import org.junit.Test;
import ru.job4j.musical.connectsdb.ConnectPSQL;
import ru.job4j.musical.entities.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

/**
 * Test RoleStore.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class RoleStoreTest {

    private RoleStore testStore = new RoleStore();

    @After
    public void cleanDB() {
        try (Connection connect = ConnectPSQL.getInstance().getConnect();
             Statement st = connect.createStatement()) {
            st.execute("DELETE FROM role WHERE role IN ('test', 'test2')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void add() {
        testStore.add(new Role("test"));
        assertTrue(testStore.findAll().contains(new Role("test")));
    }

    @Test
    public void findAll() {
        testStore.add(new Role("test"));
        testStore.add(new Role("test2"));
        assertTrue(testStore.findAll().containsAll(asList(new Role("test"), new Role("test2"))));
    }

    @Test
    public void findById() {
        this.testStore.add(new Role("test"));
        int roleId = findRoleId(new Role("test"));
        assertTrue(this.testStore.findById(roleId).equals(new Role("test")));
    }

    private int findRoleId(Role role) {
        int result = 0;
        for (Role currentRole : this.testStore.findAll()) {
            if (currentRole.equals(role)) {
                result = currentRole.getId();
            }
        }
        return result;
    }

    @Test
    public void delete() {
        this.testStore.add(new Role("test"));
        assertTrue(this.testStore.delete(this.findRoleId(new Role("test"))));
        assertFalse(this.testStore.delete(0));
        assertFalse(this.testStore.findAll().contains(new Role("test")));
    }

    @Test
    public void update() {
        Role role = new Role("test");
        Role uppedRole = new Role("test2");
        this.testStore.add(role);
        int roleId = this.findRoleId(role);
        assertTrue(this.testStore.update(roleId, uppedRole));
        assertFalse(this.testStore.update(0, new Role("test3")));
        assertTrue(this.testStore.findById(roleId).equals(uppedRole));
        assertFalse(this.testStore.findAll().contains(role));
    }
}