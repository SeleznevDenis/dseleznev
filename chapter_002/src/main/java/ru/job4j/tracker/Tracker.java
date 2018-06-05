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
    public static final Logger LOGGER = LoggerFactory.getLogger(Tracker.class);
    private Properties properties;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet result;
    private static final Random RN = new Random();

    public void init() {
        try (InputStream reader = getClass().getClassLoader().getResourceAsStream(QUERIES)) {
            this.properties = new Properties();
            this.properties.load(reader);
            this.statement = connectAndGetPStatement("tableCreate");
            this.statement.execute();
        } catch (IOException | SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            this.close();
        }
    }

    private PreparedStatement connectAndGetPStatement(String queryName)  {
        try {
            this.connection = DriverManager.getConnection(
                    this.properties.getProperty("url"),
                    this.properties.getProperty("username"),
                    this.properties.getProperty("password")
            );
            this.statement = this.connection.prepareStatement(
                    this.properties.getProperty(queryName)
            );
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return this.statement;
    }

    /**
     * Метод реализующий добавление заявки в хранилище.
     * @param item новая заявка.
     * @return
     */
     public Item add(Item item) {
         try {
             this.statement = connectAndGetPStatement("itemAdd");
             if (item.getId() == null) {
                 item.setId(this.generateId());
             }
             this.statement.setString(1, item.getId());
             this.statement.setString(2, item.getName());
             this.statement.setString(3, item.getDesc());
             this.statement.setLong(4, item.getCreated());
             this.statement.execute();
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
         try {
             this.statement = connectAndGetPStatement("itemUpdate");
             this.statement.setString(1, item.getName());
             this.statement.setString(2, item.getDesc());
             this.statement.setLong(3, item.getCreated());
             this.statement.setString(4, id);
             this.statement.execute();
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
         try {
             this.statement = connectAndGetPStatement("itemDelete");
             this.statement.setString(1, id);
             this.statement.execute();
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
         try {
             this.statement = connectAndGetPStatement("findAll");
             this.result = this.statement.executeQuery();
             while (this.result.next()) {
                 Item currentItem = new Item(
                         this.result.getString("name"),
                         this.result.getString("description"),
                         this.result.getLong("created")
                 );
                 currentItem.setId(this.result.getString("id"));
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
         try {
             this.statement = connectAndGetPStatement("foundByName");
             this.statement.setString(1, key);
             this.result = this.statement.executeQuery();
             while (this.result.next()) {
                 Item currentItem = new Item(
                         this.result.getString("name"),
                         this.result.getString("description"),
                         this.result.getLong("created")
                 );
                 currentItem.setId(this.result.getString("id"));
                 foundArr.add(currentItem);
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
         try {
             this.statement = connectAndGetPStatement("foundById");
             this.statement.setString(1, id);
             this.result = this.statement.executeQuery();
             result.next();
             found = new Item(
                     this.result.getString("name"),
                     this.result.getString("description"),
                     this.result.getLong("created")
             );
             found.setId(this.result.getString("id"));
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
            if (this.statement != null) {
                this.statement.close();
            }
            if (this.result != null) {
                this.result.close();
            }
            if (this.connection != null) {
                this.connection.close();
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
