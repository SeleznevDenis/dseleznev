package ru.job4j.musical.connectsdb;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Connect PSQL
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class ConnectPSQL {
    /**
     * LOG4J Логгер.
     */
    private static final Logger LOG = LogManager.getLogger("servlets");

    /**
     * Пул коннектов.
     */
    private static final BasicDataSource SOURCE = new BasicDataSource();

    /**
     * Инстанс класса.
     */
    private static final ConnectPSQL INSTANCE = new ConnectPSQL();

    /**
     * Настройки подключения к базе данных и запросы.
     */
    private static final Properties PROPS = new Properties();

    static {
        try (InputStream stream = ConnectPSQL.class.getClassLoader()
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
        createStructure();
    }

    /**
     * Конструктор закрыт, т.к. используется шаблон "Одиночка".
     */
    private ConnectPSQL() {
    }

    /**
     * Создает структуру в базе данных если ее нет.
     */
    private static void createStructure() {
        try (Connection con = SOURCE.getConnection();
             Statement st = con.createStatement()) {
            st.execute(PROPS.getProperty("createRole"));
            st.execute(PROPS.getProperty("createType"));
            st.execute(PROPS.getProperty("createUsers"));
            st.execute(PROPS.getProperty("createAddress"));
            st.execute(PROPS.getProperty("createTypeUsers"));
            st.execute(PROPS.getProperty("insertRoles"));
            st.execute(PROPS.getProperty("insertTypes"));
            st.execute(PROPS.getProperty("insertRootUser"));
            st.execute(PROPS.getProperty("insertRootAddress"));
            st.execute(PROPS.getProperty("insertRootMusicType"));
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * @return инстанс класса.
     */
    public static ConnectPSQL getInstance() {
        return INSTANCE;
    }

    /**
     * @return Connection.
     */
    public Connection getConnect() throws SQLException {
        return SOURCE.getConnection();
    }

    /**
     * @return Properties с подготовленными запросами.
     */
    public Properties getQueries() {
        return PROPS;
    }
}
