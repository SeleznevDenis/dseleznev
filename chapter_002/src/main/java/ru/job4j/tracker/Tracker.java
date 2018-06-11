package ru.job4j.tracker;

import java.io.*;
import java.util.*;
import java.sql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Объект класса Tracker работает с заявками типа Item.
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class Tracker implements AutoCloseable {
    private static final String QUERIES = "queries.properties";
    private static final Logger LOGGER = LoggerFactory.getLogger(Tracker.class);
    private Properties properties;
    private Connection connection;
    private static final Random RN = new Random();

    public void init() {
        try (InputStream reader = getClass().getClassLoader().getResourceAsStream(QUERIES)) {
            this.properties = new Properties();
            this.properties.load(reader);
            try (PreparedStatement statement = connectAndGetPStatement("tableCreate")) {
                statement.execute();
            }
        } catch (IOException | SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            this.close();
        }
    }

    private PreparedStatement connectAndGetPStatement(String queryName) {
        PreparedStatement statement = null;
        try {
            this.connection = DriverManager.getConnection(
                    this.properties.getProperty("url"),
                    this.properties.getProperty("username"),
                    this.properties.getProperty("password")
            );
            statement = this.connection.prepareStatement(
                    this.properties.getProperty(queryName)
            );
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return statement;
    }

    /**
     * Метод реализующий добавление заявки в хранилище.
     * @param item новая заявка.
     * @return
     */
     public Item add(Item item) {
         try (PreparedStatement statement = connectAndGetPStatement("itemAdd")) {
             if (item.getId() == null) {
                 item.setId(this.generateId());
             }
             statement.setString(1, item.getId());
             statement.setString(2, item.getName());
             statement.setString(3, item.getDesc());
             statement.setLong(4, item.getCreated());
             statement.execute();
         } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
         } finally {
             close();
         }
         return item;
     }

    /**
     * Метод заменяет заявку с заданным id в хранилище на заданную заявку.
     * @param id заданный id.
     * @param item заданная заявка.
     */
     public void replace(String id, Item item) {
         try (PreparedStatement statement = connectAndGetPStatement("itemUpdate")) {
             statement.setString(1, item.getName());
             statement.setString(2, item.getDesc());
             statement.setLong(3, item.getCreated());
             statement.setString(4, id);
             statement.execute();
         } catch (SQLException e) {
             LOGGER.error(e.getMessage(), e);
         } finally {
             close();
         }
     }

    /**
     * Метод удаляет объект хранящийся в ArrayList items.
     * @param id
     */
     public void delete(String id) {
         try (PreparedStatement statement = connectAndGetPStatement("itemDelete")) {
             statement.setString(1, id);
             statement.execute();
         } catch (SQLException e) {
             LOGGER.error(e.getMessage(), e);
         } finally {
             close();
         }
     }

    /**
     * Возвращает из хранилища ArrayList с заявками.
     * @return массив содержащий ненулевые ссылки на объекты Item.
     */
     public ArrayList<Item> findAll() {
         ArrayList<Item> result = new ArrayList<>();
         try (PreparedStatement statement = connectAndGetPStatement("findAll");
              ResultSet resultSet = statement.executeQuery()) {
             while (resultSet.next()) {
                 Item currentItem = new Item(
                         resultSet.getString("name"),
                         resultSet.getString("description"),
                         resultSet.getLong("created")
                 );
                 currentItem.setId(resultSet.getString("id"));
                 result.add(currentItem);
             }
         } catch (SQLException e) {
             LOGGER.error(e.getMessage(), e);
         } finally {
             close();
         }
        return result;
     }

    /**
     * Метод производит поиск заявок в хранилище по имени.
     * @param key заданное имя для поиска.
     * @return ArrayList содержащий все заявки с совпадающим заданным именем.
     */
     public ArrayList<Item> findByName(String key) {
         ArrayList<Item> foundArr = new ArrayList<>();
         try (PreparedStatement statement = connectAndGetPStatement("foundByName")) {
             statement.setString(1, key);
             try (ResultSet resultSet = statement.executeQuery()) {
                 while (resultSet.next()) {
                     Item currentItem = new Item(
                             resultSet.getString("name"),
                             resultSet.getString("description"),
                             resultSet.getLong("created")
                     );
                     currentItem.setId(resultSet.getString("id"));
                     foundArr.add(currentItem);
                 }
             }
         } catch (SQLException e) {
             LOGGER.error(e.getMessage(), e);
         } finally {
             close();
         }
         return foundArr;
     }

    /**
     * findById ищет заявку в хранилище по id.
     * @param id заданный для поиска id.
     * @return найденная заявка.
     */
     public Item findById(String id) {
         Item found = null;
         try (PreparedStatement statement = connectAndGetPStatement("foundById")) {
             statement.setString(1, id);
             try (ResultSet resultSet = statement.executeQuery()) {
                 resultSet.next();
                 found = new Item(
                         resultSet.getString("name"),
                         resultSet.getString("description"),
                         resultSet.getLong("created")
                 );
                 found.setId(resultSet.getString("id"));
             }
         } catch (SQLException e) {
             LOGGER.error(e.getMessage(), e);
         } finally {
             close();
         }
         return found;
     }

    /**
     * Метод генерирует уникальный ключ для заявки.
     * @return Уникальный ключ.
     */
     private String generateId() {
         return String.valueOf(System.currentTimeMillis() + RN.nextInt());
     }

    @Override
    public void close() {
        try {
            if (this.connection != null) {
                this.connection.close();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
