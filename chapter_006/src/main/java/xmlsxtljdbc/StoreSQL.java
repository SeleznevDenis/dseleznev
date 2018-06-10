package xmlsxtljdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Объект класса, работает с базой данных.
 * @author Denis Seleznev
 * @version $Id$
 * @since 10.06.2018
 */
public class StoreSQL {
    /**
     * Хранит настройки подключения к базе данных.
     */
    private Properties config;

    /**
     * Хранит SQL запросы.
     */
    private final Properties queries = new Properties();

    /**
     * slf4j логгер.
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(StoreSQL.class);

    /**
     * Инициализирует настройки подключения к базе данных.
     * @param config ссылка на объект содержащий настройки.
     */
    StoreSQL(Properties config) {
        this.config = config;
    }

    /**
     * Инициализирует properties содержащий запросы SQL.
     * Создает таблицу entry в базе данных при её отсутствии,
     * очищает таблицу entry если в ней были данные,
     */
    public void init() {
        Connection connect = connectDB();
        try (InputStream reader = getClass().getClassLoader().getResourceAsStream("queries.properties");
             Statement statement = connect.createStatement()) {
            this.queries.load(reader);
            statement.addBatch(queries.getProperty("checkTable"));
            statement.addBatch(queries.getProperty("cleanTable"));
            statement.executeBatch();
            connect.commit();
        } catch (IOException | SQLException e) {
            this.dbRollback(connect);
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                connect.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    /**
     * Подключается к базе данных, отключает режим AutoCommit
     * @return Ссылка на объект Connection
     */
    private Connection connectDB() {
        Connection currentConnection = null;
        try {
            currentConnection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
            currentConnection.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return currentConnection;
    }

    /**
     * Откатывает не произведенный в базе данных коммит
     * @param connection подключение к базе данных
     */
    private void dbRollback(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    /**
     * Заполняет таблицу entry в базе данных числами от 0 до заданного предела по порядку.
     * @param n заданный предел.
     */
    public void generate(int n) {
        Connection connect = connectDB();
        try (PreparedStatement statement = connect.prepareStatement(queries.getProperty("addElement"))) {
            for (int i = 0; i < n; i++) {
                statement.setInt(1, i);
                statement.addBatch();
            }
            statement.executeBatch();
            connect.commit();
        } catch (SQLException e) {
            dbRollback(connect);
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                connect.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    /**
     * Возвращает список данных из базы, обернутых объектами Entry.
     * @return List<StoreXML.Entry>
     */
    public List<StoreXML.Entry> getData() {
        List<StoreXML.Entry> result = new ArrayList<>();
        Connection connect = connectDB();
        try (Statement statement = connect.createStatement();
             ResultSet resultSet = statement.executeQuery(this.queries.getProperty("getData"))) {
            while (resultSet.next()) {
                result.add(new StoreXML.Entry(resultSet.getInt("field")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
