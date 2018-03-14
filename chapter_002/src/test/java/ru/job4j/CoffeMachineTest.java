package ru.job4j;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Denis Seleznev(d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class CoffeMachineTest {

    /**
     * Test changes.
     */
    @Test
    public void ifValueOneHungredAndPriceThirtyTwoThenChangesSixDozenAndFiveAndTwoAndOne() {
        CoffeeMachine testMachine = new CoffeeMachine();
        int[] result = testMachine.changes(100, 32);
        int[] expect = {10, 10, 10, 10, 10, 10, 5, 2, 1};
        assertThat(result, is(expect));
    }
}
