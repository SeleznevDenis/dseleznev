package ru.job4j.switcher;

import org.junit.Test;

import java.util.concurrent.Semaphore;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test Linker.
 * @author Denis Seleznev
 * @version $Id$
 * @since 19.05.2018
 */
public class LinkerTest {

    /**
     * Задает начальную синхронизацию с помощью семафоров.
     * Создает 2 потока, производящих запись в string.
     * ждёт 400мс, затем выставляет флаг завершения у потоков и дожидается окончания их выполнения.
     * Создает проверочную строку на основе длины string, и проверяет очередность записи потоков.
     */
    @Test
    public void tst() {
        Semaphore firstSemaphore = new Semaphore(1);
        Semaphore secondSemaphore = new Semaphore(0);
        ConcatenationString string = new ConcatenationString();
        Thread first = new Thread(new Linker(firstSemaphore, secondSemaphore, 1, string));
        Thread second = new Thread(new Linker(secondSemaphore, firstSemaphore, 2, string));
        first.start();
        second.start();
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        first.interrupt();
        second.interrupt();
        try {
            second.join();
            first.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        checkResult(string.getString(), 1);
    }

    /**
     * Создает проверочную строку на основе длинны строки для проверки, и проверяет
     * соответствие очередности записи, при условии что первым записывает поток номер 1.
     * @param result строка для проверки.
     */
    static void checkResult(String result, int firstWriter) {
        String expect = "";
        boolean firstWrite = firstWriter == 1;
        for (int i = 0; i < result.length() / 10; i++) {
            if (firstWrite) {
                expect = expect.concat("1111111111");
                firstWrite = false;
            } else {
                expect = expect.concat("2222222222");
                firstWrite = true;
            }
        }
        assertThat(result, is(expect));
    }
}
