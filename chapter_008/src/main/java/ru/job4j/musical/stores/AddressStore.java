package ru.job4j.musical.stores;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.musical.connectsdb.ConnectPSQL;
import ru.job4j.musical.entities.Address;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Хранилище адресов.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class AddressStore {
    private static final Logger LOG = LogManager.getLogger("servlets");

    /**
     * Точка подключения к базе данных.
     */
    private static final ConnectPSQL CON = ConnectPSQL.getInstance();

    /**
     * Подготовленные запросы к базе данных.
     */
    private static final Properties QUERIES = CON.getQueries();

    /**
     * Добавляет адрес в хранилище.
     * @param userId идентификатор пользователя к которому добавляем адрес.
     * @param address адрес.
     * @return boolean результат операции.
     */
    public boolean add(int userId, Address address) {
        boolean result = false;
        if (address != null) {
            try (Connection connect = CON.getConnect();
                 PreparedStatement st = connect.prepareStatement(QUERIES.getProperty("addAddress"))) {
                st.setInt(1, userId);
                st.setString(2, address.getAddress());
                st.execute();
                result = true;
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return result;
    }

    /**
     * @return список всех адресов из хранилища.
     */
    public List<Address> findAll() {
        List<Address> result = new ArrayList<>();
        try (Connection connect = CON.getConnect();
             Statement st = connect.createStatement()) {
            try (ResultSet rstSet = st.executeQuery(QUERIES.getProperty("findAllAddress"))) {
                while (rstSet.next()) {
                    result.add(new Address(rstSet.getString("address")));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Ищет адрес по идентификатору.
     * @param id идентификатор адреса.
     * @return найденный адрес.
     */
    public Address findById(int id) {
        Address result = null;
        try (Connection connect = CON.getConnect();
             PreparedStatement st = connect.prepareStatement(QUERIES.getProperty("findAddressById"))) {
            st.setInt(1, id);
            try (ResultSet rstSet = st.executeQuery()) {
                if (rstSet.next()) {
                    result = new Address(rstSet.getString("address"));
                }
            }

        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Удаляет адрес по идентификатору.
     * @param id идентификатор.
     * @return boolean результат операции.
     */
    public boolean delete(int id) {
        boolean result = false;
        try (Connection connect = CON.getConnect();
             PreparedStatement st = connect.prepareStatement(QUERIES.getProperty("deleteAddress"))) {
            st.setInt(1, id);
            result = st.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Обновляет адрес.
     * @param id идентификатор адреса для обновления.
     * @param address новые данные адреса.
     * @return boolean результат операции.
     */
    public boolean update(int id, Address address) {
        boolean result = false;
        if (address != null) {
            try (Connection connect = CON.getConnect();
                 PreparedStatement st = connect.prepareStatement(QUERIES.getProperty("updateAddress"))) {
                st.setString(1, address.getAddress());
                st.setInt(2, id);
                result = st.executeUpdate() > 0;
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return result;
    }

    /**
     * Ищет идентификатор адреса.
     * @param address адрес для поиска.
     * @return идентификатор адреса.
     */
    public int findId(Address address) {
        int result = 0;
        try (Connection con = CON.getConnect();
        PreparedStatement st = con.prepareStatement(QUERIES.getProperty("findAddressId"))) {
            st.setString(1, address.getAddress());
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
}
