package ru.job4j.stores;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.models.Task;

import java.sql.Timestamp;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.*;

public class TaskStoreTest {

    private TaskStore test;
    private Task first = new Task("test", new Timestamp(System.currentTimeMillis()), true);
    private Task second = new Task("test2", new Timestamp(System.currentTimeMillis()), false);
    private SessionFactory factory = DBSessionFactory.getSessionFactory();

    @Before
    public void setUp() {
        this.test = new TaskStore();
    }

    @After
    public void tierDown() {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query query = session.createQuery("DELETE FROM Task WHERE description = 'test'");
            query.executeUpdate();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            transaction.commit();
            session.close();
        }
    }

    @Test
    public void add() {
        this.test.add(this.first);
        assertTrue(this.test.findAll().contains(this.first));
    }

    @Test
    public void update() {
        this.test.add(this.second);
        int taskId = this.findTaskId(this.second);
        Task updatedTask = this.first;
        this.first.setId(taskId);
        this.test.update(updatedTask);
        assertThat(this.test.findById(taskId), is(this.first));
    }

    private int findTaskId(Task task) {
        int result = 0;
        for (Task foundTask : this.test.findAll()) {
            if (foundTask.equals(task)) {
                result = foundTask.getId();
                break;
            }
        }
        return result;
    }

    @Test
    public void delete() {
        this.test.add(this.first);
        int taskId = this.findTaskId(this.first);
        assertTrue(this.test.delete(taskId));
        assertFalse(this.test.delete(taskId));
        assertFalse(this.test.findAll().contains(this.first));
    }
}