package ru.job4j;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class ConnectionFactory {
    private final BasicDataSource source = new BasicDataSource();
    private final Properties props = new Properties();
    private volatile boolean done = false;

    /**
     * Инициализирует пул коннектов.
     * @param propsName имя файла содержащего конфигурацию подключения к базе.
     */
    public ConnectionFactory(String propsName) {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(propsName)) {
            this.props.load(stream);
            this.source.setDriverClassName(this.props.getProperty("driverClassName"));
            this.source.setUrl(this.props.getProperty("url"));
            this.source.setUsername(this.props.getProperty("userName"));
            this.source.setPassword(this.props.getProperty("password"));
            this.source.setMinIdle(5);
            this.source.setMaxIdle(10);
            this.source.setMaxOpenPreparedStatements(100);
            this.createStructure();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Создает структуру в базе данных.
     */
    private void createStructure() {
        try (Connection con = this.source.getConnection();
             Statement st = con.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS s_user(id SERIAL PRIMARY KEY, name varchar(50))";
            st.execute(sql);
            this.done = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return коннект к базе данных.
     */
    public Connection getConnect() {
        Connection result = null;
        if (this.done) {
            try {
                result = this.source.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
