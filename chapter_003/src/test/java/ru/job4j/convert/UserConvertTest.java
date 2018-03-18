package ru.job4j.convert;

import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class UserConvertTest {
    /**
     * Test process.
     */
    @Test
    public void usersListConvertToHashMap() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(3, "Ivan", "Spb"));
        userList.add(new User(4, "Vasiliy", "Turinsk"));
        UserConvert convertTest = new UserConvert();
        HashMap<Integer, User> result = convertTest.process(userList);
        assertThat(result.get(3).getName(), is("Ivan"));
    }

}
