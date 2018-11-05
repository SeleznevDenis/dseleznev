package ru.job4j.carplace.persistens.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.annotationstest.models.Machine;
import ru.job4j.carplace.persistens.models.Transmission;
import ru.job4j.utils.SingletonSF;
import ru.job4j.utils.TransactionWrapper;
import ru.job4j.annotationstest.annotationmodels.AEngine;
import ru.job4j.annotationstest.annotationmodels.AGearBox;
import ru.job4j.annotationstest.annotationmodels.AMachine;
import ru.job4j.annotationstest.annotationmodels.ATransmission;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CrudDAOTest {

    private static final Logger LOGGER = LogManager.getLogger("servlets");
    private static final SessionFactory SF = SingletonSF.getSessionFactory();
    private static final TransactionWrapper WRAPPER = new TransactionWrapper(LOGGER, SF);
    private CrudDAO<AMachine> testDao;
    private ATransmission testTransmission;
    private AEngine testEngine;
    private AGearBox testGearBox;
    private AMachine testMachine;

    @Before
    public void setUp() {
        this.testDao = new CrudDAO<AMachine>(SingletonSF.getSessionFactory()) { };
        this.testTransmission = new ATransmission("testTransmission");
        this.testEngine = new AEngine("testEngine");
        this.testGearBox = new AGearBox("testGearBox");
        this.testMachine = new AMachine("test", this.testTransmission, this.testEngine, this.testGearBox);
    }

    @After
    public void cleanDB() {
        WRAPPER.wrapAndExecute(session -> {
            session.createQuery("delete from AMachine where name= 'test'").executeUpdate();
            System.out.println("delete");
            session.createQuery("delete from AEngine where description = 'testEngine'").executeUpdate();
            session.createQuery("delete from AGearBox where description = 'testGearBox'").executeUpdate();
            session.createQuery("delete from ATransmission where description = 'testTransmission'").executeUpdate();
            return null;
        });
    }

    @Test
    public void add() {
        int carId = this.testDao.add(this.testMachine);
        assertThat(this.testDao.findById(carId).getName(), is("test"));
    }

    @Test
    public void delete() {
        int carId = this.testDao.add(this.testMachine);
        this.testDao.delete(new AMachine(carId));
        assertNull(this.testDao.findById(carId));
    }

    @Test
    public void update() {
        this.testMachine.setName("test2");
        int carId = this.testDao.add(this.testMachine);
        this.testMachine.setName("test");
        this.testMachine.setId(carId);
        this.testDao.update(this.testMachine);
        assertThat(this.testDao.findById(carId).getName(), is("test"));
    }

    @Test
    public void scratch() {

    }
}