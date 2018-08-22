package ru.job4j.musical.stores;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.musical.connectsdb.ConnectPSQL;
import ru.job4j.musical.entities.Address;
import ru.job4j.musical.entities.MusicType;
import ru.job4j.musical.entities.Role;
import ru.job4j.musical.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * UserStore.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class UserStore {
    private static final Logger LOG = LogManager.getLogger("servlets");

    /**
     * ConnectionPool.
     */
    private static final ConnectPSQL CON = ConnectPSQL.getInstance();

    /**
     * Запросы к базе данных.
     */
    private static final Properties QUERIES = CON.getQueries();
    private static final RoleStore ROLE_STORE = new RoleStore();
    private static final AddressStore ADDRESS_STORE = new AddressStore();
    private static final MusicTypeStore TYPE_STORE = new MusicTypeStore();

    /**
     * Добавляет пользователя в хранилище
     * @param user пользователь для добавления.
     * @return boolean результат операции.
     */
    public boolean add(User user) {
        boolean done = false;
        if (user != null && this.findByLogin(user.getLogin()) == null) {
            try (Connection connect = CON.getConnect();
                 PreparedStatement st = connect.prepareStatement(QUERIES.getProperty("insertUser"))) {
                int roleId = ROLE_STORE.findRoleId(user.getRole());
                if (roleId == 0) {
                    roleId = ROLE_STORE.add(user.getRole());
                }
                st.setInt(1, roleId);
                st.setString(2, user.getPassword());
                st.setString(3, user.getLogin());
                st.setString(4, user.getName());
                int userId = 0;
                try (ResultSet rstSet = st.executeQuery()) {
                    if (rstSet.next()) {
                        userId = rstSet.getInt("id");
                        ADDRESS_STORE.add(userId, user.getAddress());
                        done = true;
                    }
                }
                this.insertUserType(userId, user);
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return done;
    }

    /**
     * Добавляет связи между музыкальными типами и пользователями.
     * @param userId идентификатор пользователя
     * @param user пользователь
     */
    private void insertUserType(int userId, User user) {
        try (Connection con = CON.getConnect();
        PreparedStatement st = con.prepareStatement(QUERIES.getProperty("addUserType"))) {
            for (MusicType currentType : user.getMusicType()) {
                int musicTypeId = TYPE_STORE.findTypeId(currentType);
                if (musicTypeId == 0) {
                    musicTypeId = TYPE_STORE.add(currentType);
                }
                st.clearParameters();
                st.setInt(1, musicTypeId);
                st.setInt(2, userId);
                st.execute();
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * @return List всех пользователей.
     */
    public List<User> findAll() {
        List<User> result = new ArrayList<>();
        try (Connection con = CON.getConnect();
             PreparedStatement st = con.prepareStatement(QUERIES.getProperty("findUsers"));
             ResultSet rstSet = st.executeQuery()) {
            while (rstSet.next()) {
                result.add(this.createUserByRstSet(rstSet));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Создает пользователя на основе resultSet.
     * @param rstSet ResultSet из базы данных.
     * @return User
     */
    private User createUserByRstSet(ResultSet rstSet) throws SQLException {
        int userId = rstSet.getInt("id");
        User foundUsr = new User(
                rstSet.getString("name"),
                rstSet.getString("password"),
                rstSet.getString("login")
        );
        foundUsr.setAddress(ADDRESS_STORE.findById(userId));
        foundUsr.setId(userId);
        foundUsr.setMusicType(findTypesByUserId(userId));
        foundUsr.setRole(ROLE_STORE.findById(rstSet.getInt("role_id")));
        return foundUsr;
    }

    /**
     * Ищет тип по идентификатору пользователя.
     * @param userId идентификатор пользователя.
     * @return List<MusicType>.
     */
    private List<MusicType> findTypesByUserId(int userId) {
        List<MusicType> result = new ArrayList<>();
        try (Connection con = CON.getConnect();
        PreparedStatement st = con.prepareStatement(QUERIES.getProperty("findTypesByUserId"))) {
            st.setInt(1, userId);
            try (ResultSet rstSet = st.executeQuery()) {
                while (rstSet.next()) {
                    result.add(TYPE_STORE.findById(rstSet.getInt("music_type_id")));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Ищет пользователя по идентификатору.
     * @param id идентификатор пользователя.
     * @return User.
     */
    public User findById(int id) {
        User result = null;
        try (Connection con = CON.getConnect();
        PreparedStatement st = con.prepareStatement(QUERIES.getProperty("findUserById"))) {
            st.setInt(1, id);
            try (ResultSet rstSet = st.executeQuery()) {
                if (rstSet.next()) {
                    result = createUserByRstSet(rstSet);
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Ищет пользователя по логину.
     * @param login Логин.
     * @return User.
     */
    public User findByLogin(String login) {
        User result = null;
        try (Connection con = CON.getConnect();
        PreparedStatement st = con.prepareStatement(QUERIES.getProperty("findUserByLogin"))) {
            st.setString(1, login);
            try (ResultSet rstSet = st.executeQuery()) {
                if (rstSet.next()) {
                    result = this.createUserByRstSet(rstSet);
                }
            }

        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }

        return result;
    }

    /**
     * Ищет пользователя по адресу.
     * @param address Адрес.
     * @return User.
     */
    public User findByAddress(Address address) {
        return this.findById(ADDRESS_STORE.findId(address));
    }

    /**
     * Ищет пользователя по роли.
     * @param role роль.
     * @return User.
     */
    public List<User> findByRole(Role role) {
        List<User> result = new ArrayList<>();
        try (Connection con = CON.getConnect();
        PreparedStatement st = con.prepareStatement(QUERIES.getProperty("findUsersByRole"))) {
            st.setInt(1, ROLE_STORE.findRoleId(role));
            try (ResultSet rstSet = st.executeQuery()) {
                while (rstSet.next()) {
                    result.add(createUserByRstSet(rstSet));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Ищет пользователей по музыкальному типу.
     * @param type музыкальный тип.
     * @return Users
     */
    public List<User> findByType(MusicType type) {
        List<User> result = new ArrayList<>();
        try (Connection con = CON.getConnect();
        PreparedStatement st = con.prepareStatement(QUERIES.getProperty("findUsersByType"))) {
            st.setInt(1, TYPE_STORE.findTypeId(type));
            try (ResultSet rstSet = st.executeQuery()) {
                while (rstSet.next()) {
                    result.add(createUserByRstSet(rstSet));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Удаляет связь Юзер - музыкальный тип.
     * @param connect Подключение к БД.
     * @param userId Идентификатор пользователя.
     */
    private void deleteUserType(Connection connect, int userId) throws SQLException {
        try (PreparedStatement st = connect.prepareStatement(QUERIES.getProperty("deleteUserType"))) {
            st.setInt(1, userId);
            st.execute();
        }
    }

    /**
     * Удаляет пользователя по идентификатору.
     * @param id идентификатор пользователя.
     * @return boolean результат операции.
     */
    public boolean delete(int id) {
        boolean done = false;
        if (ADDRESS_STORE.delete(id)) {
            try (Connection con = CON.getConnect()) {
                this.deleteUserType(con, id);
                try (PreparedStatement st2 = con.prepareStatement(QUERIES.getProperty("deleteUser"))) {
                    st2.setInt(1, id);
                    done = st2.executeUpdate() > 0;
                }
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return done;
    }

    /**
     * Обновляет пользователя.
     * @param id идентификатор обновляемого пользователя.
     * @param user новые данные пользователя.
     * @return boolean результат операции.
     */
    public boolean update(int id, User user) {
        boolean done = false;
        if (ADDRESS_STORE.update(id, user.getAddress())) {
            try (Connection con = CON.getConnect();
            PreparedStatement upUsr = con.prepareStatement(QUERIES.getProperty("updateUser"))) {
                upUsr.setInt(1, ROLE_STORE.findRoleId(user.getRole()));
                upUsr.setString(2, user.getPassword());
                upUsr.setString(3, user.getLogin());
                upUsr.setString(4, user.getName());
                upUsr.setInt(5, id);
                upUsr.execute();
                this.deleteUserType(con, id);
                this.insertUserType(id, user);
                done = true;
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return done;
    }
}
