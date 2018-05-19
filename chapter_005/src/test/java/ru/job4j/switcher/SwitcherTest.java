package ru.job4j.switcher;

import org.junit.Test;

/**
 * Test Switcher.
 * @author Denis Seleznev
 * @version $Id$
 * @since 19.05.2018
 */
public class SwitcherTest {

    @Test
    public void test() {
        Switcher testSwitcher = new Switcher(1);
        testSwitcher.startSwitching();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String result = testSwitcher.stopSwitchingWithResult();
        LinkerTest.checkResult(result, 1);
    }
}