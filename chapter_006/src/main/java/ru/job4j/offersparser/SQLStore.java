package ru.job4j.offersparser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.Set;

/**
 * Объект класса, работает с базой данных.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class SQLStore implements AutoCloseable {

    /**
     * LOG4j2
     */
    private static final Logger LOG = LogManager.getLogger("forParser");

    /**
     * Хранит настройки подключения.
     */
    private Properties config = new Properties();

    /**
     * Хранит используемые SQL запросы.
     */
    private Properties queries = new Properties();

    /**
     * Хранит ссылку на текущее подключение к базе данных.
     */
    private Connection connect;

    /**
     * Инициализирует настройки подключения, и используемые SQL запросы.
     * Создает таблицу vacancy в базе данных, если её нет.
     */
    public void init() {
        try (InputStream streamConfig = getResourceAsStream("config.properties");
             InputStream streamQueries = getResourceAsStream("parser/queries.properties")) {
            this.config.load(streamConfig);
            this.queries.load(streamQueries);
            this.connect = DriverManager.getConnection(
                    this.config.getProperty("url"),
                    this.config.getProperty("username"),
                    this.config.getProperty("password")
            );
            this.createTableIfNotExists();
            LOG.info("The database is connected");
        } catch (IOException | SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Загружает ресурсы.
     * @param name имя файла.
     * @return Stream ресурса
     */
    private InputStream getResourceAsStream(String name) {
        return getClass().getClassLoader().getResourceAsStream(name);
    }

    /**
     * Создает таблицу vacancy в базе данных, если таблица отсутствует.
     */
    private void createTableIfNotExists() {
        try (Statement statement = this.connect.createStatement()) {
            statement.execute(this.queries.getProperty("createTableIfNotExists"));
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * @return дату самой свежей вакансии.
     */
    public Calendar getLastDate() {
        Calendar result = null;
        try (ResultSet resultSet = this.connect.createStatement()
                .executeQuery(this.queries.getProperty("getLastDate"))) {
            resultSet.next();
            Timestamp timestamp = resultSet.getTimestamp("date");
            if (timestamp != null) {
                result = new GregorianCalendar();
                result.setTimeInMillis(timestamp.getTime());
            }
            LOG.info("Last founded vacancy date was checked");
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * Добавляет вакансии из HashSet в базу данных.
     * @param vacanciesSet HashSet вакансий.
     */
    public void insertVacancies(Set<Vacancy> vacanciesSet) {
        try (PreparedStatement statement = this.connect
                .prepareStatement(this.queries.getProperty("insertVacancy"))) {
            for (Vacancy currentVacancy : vacanciesSet) {
                statement.setString(1, currentVacancy.getText());
                statement.setString(2, currentVacancy.getUrl());
                statement.setTimestamp(3, new Timestamp(currentVacancy.getDate().getTime().getTime()));
                statement.addBatch();
            }
            statement.executeBatch();
            LOG.info("Vacancies are recorded in the database");
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Закрывает подключение к базе данных.
     */
    @Override
    public void close() {
        if (this.connect != null) {
            try {
                this.connect.close();
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }
}
