package ru.job4j.musical.stores;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.musical.connectsdb.ConnectPSQL;
import ru.job4j.musical.entities.MusicType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Хранилище музыкальных типов.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class MusicTypeStore {
    /**
     * LOG4J логгер.
     */
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
     * Добавляет музыкальный тип в хранилище.
     * @param type тип для добавления.
     * @return идентификатор добавленного типа.
     */
    public int add(MusicType type) {
        int typeId = 0;
        if (!this.findAll().contains(type)) {
            try (Connection connect = CON.getConnect();
                 PreparedStatement st = connect.prepareStatement(QUERIES.getProperty("addMusicType"))) {
                st.setString(1, type.getType());
                try (ResultSet rstSet = st.executeQuery()) {
                    if (rstSet.next()) {
                        typeId = rstSet.getInt("id");
                    }
                }
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return typeId;
    }

    /**
     * Ищет идентификатор типа.
     * @param type музыкальный тип для поиска.
     * @return найденный идентификатор.
     */
    public int findTypeId(MusicType type) {
        int result = 0;
        try (Connection connect = CON.getConnect();
        PreparedStatement st = connect.prepareStatement(QUERIES.getProperty("findTypeId"))) {
            st.setString(1, type.getType());
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
     * @return список всех музыкальных типов из хранилища.
     */
    public List<MusicType> findAll() {
        List<MusicType> result = new ArrayList<>();
        try (Connection connect = CON.getConnect();
             Statement st = connect.createStatement();
             ResultSet rstSet = st.executeQuery(QUERIES.getProperty("findAllTypes"))) {
            while (rstSet.next()) {
                MusicType founded = new MusicType(rstSet.getString("music_type"));
                founded.setId(rstSet.getInt("id"));
                result.add(founded);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Ищет музыкальный тип по идентификатору.
     * @param id идентификатор музыкального типа.
     * @return найденный музыкальный тип.
     */
    public MusicType findById(int id) {
        MusicType result = null;
        try (Connection connect = CON.getConnect();
             PreparedStatement st = connect.prepareStatement(QUERIES.getProperty("findTypeById"))) {
            st.setInt(1, id);
            try (ResultSet rstSet = st.executeQuery()) {
                if (rstSet.next()) {
                    result = new MusicType(rstSet.getString("music_type"));
                    result.setId(rstSet.getInt("id"));
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Удаляет музыкальный тип по идентификатору.
     * @param id идентификатор.
     * @return boolean результат операции.
     */
    public boolean delete(int id) {
        boolean result = false;
        if (this.findById(id) != null) {
            try (Connection connect = CON.getConnect();
                 PreparedStatement st = connect.prepareStatement(QUERIES.getProperty("deleteType"))) {
                st.setInt(1, id);
                st.execute();
                result = true;
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return result;
    }

    /**
     * Обновляет музыкальный тип.
     * @param id идентификатор обновляемого музыкального типа.
     * @param type новые данные музыкального типа.
     * @return boolean результат операции.
     */
    public boolean update(int id, MusicType type) {
        boolean result = false;
        if (type != null && this.findById(id) != null) {
            try (Connection connect = CON.getConnect();
                 PreparedStatement st = connect.prepareStatement(QUERIES.getProperty("updateType"))) {
                st.setString(1, type.getType());
                st.setInt(2, id);
                st.execute();
                result = true;
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return result;
    }
}
