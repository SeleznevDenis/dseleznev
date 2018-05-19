package ru.job4j.switcher;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ConcatenationStringTest {

    @Test
    public void ifConcatenateToString6and7GetStringShouldBeReturn67() {
        ConcatenationString testString = new ConcatenationString();
        testString.concatenate(6);
        testString.concatenate(7);
        assertThat(testString.getString(), is("67"));
    }
}