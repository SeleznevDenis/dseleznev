package ru.job4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * JDBC Storage.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */

public class JdbcStorage implements Storage {

    private final ConnectionFactory factory;

    public JdbcStorage(ConnectionFactory factory) {
        this.factory = factory;
    }

    /**
     * Добавляет объект в хранилище.
     * @param user объект для добавления.
     */
    @Override
    public int add(User user) {
        System.out.println("jdbc add");
        int id = 0;
        try (Connection con = this.factory.getConnect();
             PreparedStatement st = con.prepareStatement("INSERT INTO s_user(name) values(?) RETURNING ID")) {
            st.setString(1, user.getName());
            try (ResultSet rst = st.executeQuery()) {
                while (rst.next()) {
                    id = rst.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * @param id идентификатор искоого пользователя.
     * @return искомый пользователь.
     */
    @Override
    public User findById(int id) {
        User result = null;
        try (Connection con = this.factory.getConnect();
             PreparedStatement st = con.prepareStatement("SELECT name FROM s_user WHERE id = ?")) {
            st.setInt(1, id);
            try (ResultSet rst = st.executeQuery()) {
                while (rst.next()) {
                    result = new User();
                    result.setId(id);
                    result.setName(rst.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
