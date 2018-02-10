package ru.job4j;

import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
*Test.
*
*@author Denis Seleznev (d.selezneww@mail.ru)
*@version $Id$
*@since 0.1
*/
public class CalculateTest {
	/**
	*Test echo.
	*/ @Test
	public void whenTakeNameThenThreeEchoPlusName() {
		String input = "Denis Seleznev";
		String expect = "Echo, echo, echo : Denis Seleznev";
		Calculate calc = new Calculate();
		String result = calc.echo(input);
		assertThat(result, is(expect));
	}	
}	