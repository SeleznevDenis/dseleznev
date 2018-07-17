package ru.job4j.servlets;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

/**
 * Объект класса взаимодействует с базой данных.
 * @author Denis Seleznev.
 * @version $Id$
 * @since 0.1
 */
public class DbStore implements Store, UserAddressStore {
    private static final Logger LOG = LogManager.getLogger("servlets");

    /**
     * Пул коннектов.
     */
    private static final BasicDataSource SOURCE = new BasicDataSource();

    /**
     * Инстанс класса.
     */
    private static final DbStore INSTANCE = new DbStore();

    /**
     * Настройки подключения к базе данных и запросы.
     */
    private static final Properties PROPS = new Properties();

    static {
        try (InputStream stream = DbStore.class.getClassLoader()
                       .getResourceAsStream("queries.properties")) {
            PROPS.load(stream);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        SOURCE.setDriverClassName("org.postgresql.Driver");
        SOURCE.setUrl(PROPS.getProperty("url"));
        SOURCE.setUsername(PROPS.getProperty("user"));
        SOURCE.setPassword(PROPS.getProperty("password"));
        SOURCE.setMinIdle(5);
        SOURCE.setMaxIdle(10);
        SOURCE.setMaxOpenPreparedStatements(100);
        createTableAndRootUser();
    }

    /**
     * Создает таблицу в базе данных при инициализации класса DbStore
     * Если таблицы не существует.
     */
    private static void createTableAndRootUser() {
        try (Connection connect = SOURCE.getConnection();
             Statement st = connect.createStatement()) {
            st.execute(PROPS.getProperty("createCountry"));
            st.execute(PROPS.getProperty("fillCountries"));
            st.execute(PROPS.getProperty("createCity"));
            st.execute(PROPS.getProperty("fillCities"));
            st.execute(PROPS.getProperty("createTable"));
            st.execute(PROPS.getProperty("insertRoot"));
            st.execute(PROPS.getProperty("createIndex"));
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }


    /**
     * Конструктор закрыт, т.к. используется шаблон "Одиночка".
     */
    private DbStore() {
    }

    /**
     * @return инстанс класса.
     */
    public static DbStore getInstance() {
        return INSTANCE;
    }

    /**
     * Ищет пользователя по логину.
     * @param login логин искомого пользователя.
     * @return найденный пользователь.
     */
    @Override
    public User findByLogin(String login) {
        User foundUser = null;
        try (Connection connect = SOURCE.getConnection();
             PreparedStatement st = connect.prepareStatement(
                     PROPS.getProperty("findByLogin")
             )) {
            st.setString(1, login);
            try (ResultSet rstSet = st.executeQuery()) {
                if (rstSet.next()) {
                    foundUser = this.createUserFromRstSet(rstSet);
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return foundUser;
    }



    /**
     * Добавляет пользователя в базу данных.
     * @param user пользователь для добавления.
     */
    @Override
    public void add(User user) {
        try (Connection connect = SOURCE.getConnection();
             PreparedStatement st = connect.prepareStatement(
                     PROPS.getProperty("addUser")
             )) {
            st.setString(1, user.getName());
            st.setString(2, user.getLogin());
            st.setString(3, user.getEmail());
            st.setTimestamp(4, new Timestamp(user.getCreateDate().getTimeInMillis()));
            st.setString(5, user.getPassword());
            st.setString(6, user.getRole());
            st.setString(7, user.getCity());
            st.execute();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Обновляет пользователя в базе данных.
     * @param newUser данные для обновления.
     */
    @Override
    public void update(User newUser) {
        try (Connection con = SOURCE.getConnection();
             PreparedStatement st = con.prepareStatement(
                     PROPS.getProperty("updateUser")
             )) {
            st.setString(1, newUser.getName());
            st.setString(2, newUser.getLogin());
            st.setString(3, newUser.getEmail());
            st.setString(4, newUser.getPassword());
            st.setString(5, newUser.getRole());
            st.setString(6, newUser.getCity());
            st.setInt(7, newUser.getId());
            st.execute();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }

    }

    /**
     * Удаляет пользователя из базы данных по идентификатору.
     * @param userId идентификатор удаляемого пользователя.
     */
    @Override
    public void delete(int userId) {
        try (Connection con = SOURCE.getConnection();
             PreparedStatement st = con.prepareStatement(
                     PROPS.getProperty("deleteUser")
             )) {
            st.setInt(1, userId);
            st.execute();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Создает объект User из данных ResultSet
     * @param rst ResultSet для создания пользователя
     * @return User
     */
    private User createUserFromRstSet(ResultSet rst) throws SQLException {
        User usr = new User();
        usr.setId(rst.getInt("id"));
        usr.setName(rst.getString("name"));
        usr.setLogin(rst.getString("login"));
        usr.setEmail(rst.getString("email"));
        Calendar createDate = new GregorianCalendar();
        createDate.setTimeInMillis(
                rst.getTimestamp("create_date").getTime()
        );
        usr.setCreateDate(createDate);
        usr.setPassword(rst.getString("password"));
        usr.setRole(rst.getString("role"));
        usr.setCity(rst.getString("city"));
        usr.setCountry(rst.getString("country"));
        return usr;
    }

    /**
     * @return List содержащий всех пользователей из базы данных.
     */
    @Override
    public List<User> findAll() {
        List<User> resultList = new ArrayList<>();
        try (Connection con = SOURCE.getConnection();
             Statement st = con.createStatement();
             ResultSet rst = st.executeQuery(PROPS.getProperty("findAll"))) {
            while (rst.next()) {
                resultList.add(this.createUserFromRstSet(rst));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return resultList;
    }

    /**
     * Ищет пользователя по идентификатору.
     * @param id идентификатор пользователя.
     * @return найденный пользователь.
     */
    @Override
    public User findById(int id) {
        User user = null;
        try (Connection con = SOURCE.getConnection();
             PreparedStatement st = con.prepareStatement(PROPS.getProperty("findById"))) {
            st.setInt(1, id);
            try (ResultSet rstSet = st.executeQuery()) {
                if (rstSet.next()) {
                    user = this.createUserFromRstSet(rstSet);
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return user;
    }

    public List<String> findAllCountries() {
        List<String> resultList = new ArrayList<>();
        try (Connection con = SOURCE.getConnection();
             Statement st = con.createStatement();
             ResultSet rst = st.executeQuery(PROPS.getProperty("getAllCountries"))) {
            while (rst.next()) {
                resultList.add(rst.getString("name"));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return resultList;
    }

    public List<String> findCitiesByCountry(String name) {
        List<String> resultList = new ArrayList<>();
        try (Connection con = SOURCE.getConnection();
             PreparedStatement st = con.prepareStatement(PROPS.getProperty("getCitiesByCountry"))) {
            st.setString(1, name);
            try (ResultSet rstSet = st.executeQuery()) {
                while (rstSet.next()) {
                    resultList.add(rstSet.getString("name"));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return resultList;
    }
}
