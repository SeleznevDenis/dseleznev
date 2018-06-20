package ru.job4j.offersparser;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Пример запуска парсера.
 * @author Denis Seleznev
 * @version $Id$
 * @since 0.1
 */
public class JobPickerTest {

    @Ignore
    @Test
    public void jobPickerT() {
        JobPicker test = new JobPicker();
        test.start();
        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("finish");
    }
}