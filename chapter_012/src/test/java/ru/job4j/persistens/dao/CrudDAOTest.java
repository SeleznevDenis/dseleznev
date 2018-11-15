package ru.job4j.persistens.dao;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.jdbc.Work;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.persistens.interfaces.IEntity;
import ru.job4j.utils.SingletonSF;
import ru.job4j.utils.TransactionWrapper;

import java.sql.Connection;
import java.sql.SQLException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public abstract class CrudDAOTest<T extends IEntity> {

    protected abstract CrudDAO<T> getTestDao();
    protected abstract T getTestEntity();
    protected abstract T getSecondTestEntity();

    private static final Logger LOGGER = LogManager.getLogger("servlets");

    /**
     * Выполняет команды с тестовым контекстом к базе данных , с помощью liquibase.
     */
    /*@BeforeClass
    public static void insertToDB() {
        new TransactionWrapper(LOGGER, SingletonSF.getSessionFactory()).wrapAndExecute(
                session -> {
                    session.doWork(new Work() {
                        @Override
                        public void execute(Connection con) throws SQLException {
                            try {
                                JdbcConnection connection = new JdbcConnection(con);
                                Database base = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection);
                                Liquibase liquibase = new Liquibase(
                                        getClass().getClassLoader().getResource("db.changelog.xml").getFile(),
                                        new FileSystemResourceAccessor(),
                                        base
                                );

                                liquibase.update("test");
                                System.out.println("update");
                            } catch (LiquibaseException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    return null;
                }
        );
    }*/

    @Test
    public void add() {
        int entityId = this.getTestDao().add(this.getTestEntity());
        T resultEntity = this.getTestDao().findById(entityId);
        resultEntity.setId(0);
        assertThat(resultEntity, is(this.getTestEntity()));
    }

    @Test
    public void delete() {
        int entityId = this.getTestDao().add(this.getTestEntity());
        T entity = this.getTestEntity();
        entity.setId(entityId);
        this.getTestDao().delete(entity);
        assertNull(this.getTestDao().findById(entityId));
    }

    @Test
    public void update() {
        int entityId = this.getTestDao().add(this.getTestEntity());
        T upEntity = this.getSecondTestEntity();
        upEntity.setId(entityId);
        this.getTestDao().update(upEntity);
        assertThat(this.getTestDao().findById(entityId), is(upEntity));
    }
}