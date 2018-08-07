package ru.job4j.musical.stores;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.musical.connectsdb.ConnectPSQL;
import ru.job4j.musical.entities.Role;
import ru.job4j.musical.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Хранилище ролей.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class RoleStore {
    /**
     * LOG4j логгер.
     */
    private static final Logger LOG = LogManager.getLogger("servlets");

    /**
     * Пул коннектов.
     */
    private static final ConnectPSQL CON = ConnectPSQL.getInstance();

    /**
     * Подготовленные запросы к БД.
     */
    private static final Properties QUERIES = CON.getQueries();

    /**
     * Хранилище пользователей.
     */
    private static final UserStore USER_STORE = new UserStore();

    /**
     * Добавляет роль в хранилище.
     * @param role роль для добавления.
     * @return идентификатор добавленной роли.
     */
    public int add(Role role) {
        int result = 0;
        if (role != null && !findAll().contains(role)) {
            try (Connection connect = CON.getConnect();
                 PreparedStatement st = connect.prepareStatement(QUERIES.getProperty("addRole"))) {
                st.setString(1, role.getRole());
                try (ResultSet rstSet = st.executeQuery()) {
                    if (rstSet.next()) {
                        result = rstSet.getInt("id");
                    }
                }
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return result;
    }

    /**
     * Возвращает все роли из хранилища.
     * @return список ролей.
     */
    public List<Role> findAll() {
        List<Role> result = new ArrayList<>();
        try (Connection connect = CON.getConnect();
             Statement st = connect.createStatement();
             ResultSet rstSet = st.executeQuery(QUERIES.getProperty("findAllRoles"))) {
            while (rstSet.next()) {
                Role role = new Role(rstSet.getString("role"));
                role.setId(rstSet.getInt("id"));
                result.add(role);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Ищет роль по идентификатору.
     * @param id идентификатор роли.
     * @return найденная роль.
     */
    public Role findById(int id) {
        Role result = null;
        try (Connection connect = CON.getConnect();
             PreparedStatement st = connect.prepareStatement(QUERIES.getProperty("findRoleById"))) {
            st.setInt(1, id);
            try (ResultSet rstSet = st.executeQuery()) {
                if (rstSet.next()) {
                    result = new Role(rstSet.getString("role"));
                    result.setId(rstSet.getInt("id"));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Удаление роли по идентификатору.
     * @param id идентификатор роли.
     * @return boolean результат операции.
     */
    public boolean delete(int id) {
        boolean done = false;
        if (this.findById(id) != null) {
            try (Connection connect = CON.getConnect();
                 PreparedStatement st = connect.prepareStatement(QUERIES.getProperty("deleteRole"))) {
                st.setInt(1, id);
                st.execute();
                done = true;
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return done;
    }

    /**
     * Обновляет роль.
     * @param id идентификатор обновляемой роли.
     * @param role новые данные роли.
     * @return boolean результат операции.
     */
    public boolean update(int id, Role role) {
        boolean done = false;
        if (this.findById(id) != null) {
            try (Connection connect = CON.getConnect();
                 PreparedStatement st = connect.prepareStatement(QUERIES.getProperty("updateRole"))) {
                st.setString(1, role.getRole());
                st.setInt(2, id);
                st.execute();
                done = true;
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return done;
    }

    /**
     * Ищет идентификатор роли.
     * @param role роль для поиска.
     * @return идентифиатор роли.
     */
    public int findRoleId(Role role) {
        int result = 0;
        try (Connection connect = CON.getConnect();
             PreparedStatement st = connect.prepareStatement(QUERIES.getProperty("findRoleId"))) {
            st.setString(1, role.getRole());
            try (ResultSet rstSet = st.executeQuery()) {
                if (rstSet.next()) {
                    result = rstSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Ищет всех пользователей с заданной ролью.
     * @param role заданная роль.
     * @return список пользователей.
     */
    public List<User> findUsersByRole(Role role) {
        return USER_STORE.findByRole(role);
    }
}
