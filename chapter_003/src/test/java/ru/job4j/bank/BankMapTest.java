package ru.job4j.bank;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class BankMapTest {

    @Test
    public void ifAddUserThenUserValueEmptyArrayList() throws ItemNotFoundException {
        BankMap testMap = new BankMap();
        testMap.addUser(new User("Ivan", "660"));
        List<Account> result = testMap.getUserAccounts("660");
        assertThat(result, is(new ArrayList<Account>()));
    }

    @Test
    public void ifDeleteUserThenUserAccountsIsNull() {
        BankMap testMap = new BankMap();
        User testUser = new User("Ivan", "660");
        testMap.addUser(testUser);
        testMap.deleteUser(testUser);
        try {
            testMap.getUserAccounts("660");
        } catch (ItemNotFoundException enfe) {
            assertThat(enfe.getMessage(), is("Пользователь не найден"));
        }
    }

    @Test
    public void ifAddTwoAccountsToUserThenUserHaveTwoAccountWithRequisites()
            throws DuplicateItemException, ItemNotFoundException {
        BankMap testMap = new BankMap();
        testMap.addUser(new User("Ivan", "660"));
        Account testAcc = new Account("testReq");
        Account testAcc2 = new Account("testReq2");
        List<Account> expect = new ArrayList<>(asList(testAcc, testAcc2));
        testMap.addAccountToUser("660", testAcc);
        testMap.addAccountToUser("660", testAcc2);
        List<Account> result = testMap.getUserAccounts("660");
        assertThat(result, is(expect));
    }

    @Test
    public void ifAddTwoAccountsToUserAndDeleteOneAccountThenUserHaveOneAccount()
            throws DuplicateItemException, ItemNotFoundException {
        BankMap testMap = new BankMap();
        testMap.addUser(new User("Ivan", "660"));
        Account testAcc = new Account("testReq");
        Account testAcc2 = new Account("testReq2");
        testMap.addAccountToUser("660", testAcc);
        testMap.addAccountToUser("660", testAcc2);
        testMap.deleteAccountFromUser("660", testAcc2);
        List<Account> expect = new ArrayList<>(Collections.singletonList(testAcc));
        assertThat(testMap.getUserAccounts("660"), is(expect));
    }

    @Test
    public void ifTransferMoneyFromUserAccountToAnotherAccountThanBooleanTrue()
            throws NotEnoughMoneyException, DuplicateItemException, ItemNotFoundException {
        BankMap testMap = new BankMap();
        testMap.addUser(new User("Ivan", "660"));
        Account testAcc = new Account("testReq");
        testAcc.changeValue(500);
        Account testAcc2 = new Account("testReq2");
        testMap.addAccountToUser("660", testAcc);
        testMap.addAccountToUser("660", testAcc2);
        boolean result = testMap.transferMoney("660", "testReq", "660", "testReq2", 500);
        assertThat(result, is(true));
    }

    @Test
    public void ifTransferMoneyFromUserAccountToAnotherAccountThenFirstAccValueDiminish()
            throws NotEnoughMoneyException, DuplicateItemException, ItemNotFoundException {
        BankMap testMap = new BankMap();
        testMap.addUser(new User("Ivan", "660"));
        Account testAcc = new Account("testReq");
        testAcc.changeValue(500);
        Account testAcc2 = new Account("testReq2");
        testMap.addAccountToUser("660", testAcc);
        testMap.addAccountToUser("660", testAcc2);
        testMap.transferMoney("660", "testReq", "660", "testReq2", 500);
        assertThat(testAcc.getValue(), is(0D));
    }
    @Test
    public void ifTransferMoneyFromUserAccountToAnotherAccountThenSecondAccValueIncrease()
            throws NotEnoughMoneyException, DuplicateItemException, ItemNotFoundException {
        BankMap testMap = new BankMap();
        testMap.addUser(new User("Ivan", "660"));
        Account testAcc = new Account("testReq");
        testAcc.changeValue(500);
        Account testAcc2 = new Account("testReq2");
        testMap.addAccountToUser("660", testAcc);
        testMap.addAccountToUser("660", testAcc2);
        testMap.transferMoney("660", "testReq", "660", "testReq2", 500);
        assertThat(testAcc2.getValue(), is(500D));
    }

    @Test
    public void ifTransferMoneyFromUserAccountOnWhichNotEnoughMoney()
            throws DuplicateItemException, ItemNotFoundException {
        BankMap testMap = new BankMap();
        testMap.addUser(new User("Ivan", "660"));
        testMap.addAccountToUser("660", new Account("testReq"));
        testMap.addAccountToUser("660", new Account("testReq2"));
        try {
            testMap.transferMoney("660", "testReq", "660", "testReq2", 500);
        } catch (NotEnoughMoneyException neme) {
            assertThat(neme.getMessage(), is("Недостаточно денег для перевода."));
        }
    }

    @Test
    public void ifTransferMoneyWithInvalidAmountThenTransferReturnFalse()
            throws DuplicateItemException, ItemNotFoundException, NotEnoughMoneyException {
        BankMap testMap = new BankMap();
        testMap.addUser(new User("Ivan", "660"));
        testMap.addAccountToUser("660", new Account("testReq"));
        testMap.addAccountToUser("660", new Account("testReq2"));
        boolean result = testMap.transferMoney("660", "testReq", "660", "testReq2", -20);
        assertThat(result, is(false));
    }

    @Test
    public void ifTransferMoneyTrueThenReturnTrue()
            throws NotEnoughMoneyException, DuplicateItemException, ItemNotFoundException {
        BankMap testMap = new BankMap();
        testMap.addUser(new User("Ivan", "660"));
        Account testAcc = new Account("testReq");
        testAcc.changeValue(500);
        testMap.addUser(new User("Oleg", "100"));
        Account testAcc2 = new Account("testReq2");
        testMap.addAccountToUser("660", testAcc);
        testMap.addAccountToUser("100", testAcc2);
        boolean result = testMap.transferMoney("660", "testReq", "100", "testReq2", 500);
        assertThat(result, is(true));
    }
}
