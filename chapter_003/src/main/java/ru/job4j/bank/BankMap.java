package ru.job4j.bank;

import java.util.*;

/**
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class BankMap {

    private Map<User, List<Account>> bankMap = new HashMap<>();

    /**
     * Добавляет пользователя в хранилище, если его там еще нет.
     * @param user пользователь.
     */
    public void addUser(User user) {
        bankMap.putIfAbsent(user, new ArrayList<>());
    }

    /**
     * Удаляет пользователя из хранилища
     * @param user пользователь.
     */
    public void deleteUser(User user) {
        bankMap.remove(user);
    }

    /**
     * Ищет аккаунт пользователя по паспорту и реквизитам.
     * @param passport паспорт.
     * @param requisites реквизиты.
     * @return индекс аккаунта в листе аккаунтов пользователя.
     * @throws ItemNotFoundException в случае, если аккаунт не найден.
     */
    private int findAccountIndex(String passport, String requisites) throws ItemNotFoundException {
        int accountIndex = -1;
        List<Account> accountList = getUserAccounts(passport);
        for (Account userAccount : accountList) {
            if (userAccount.getRequisites().equals(requisites)) {
                accountIndex = accountList.indexOf(userAccount);
                break;
            }
        }
        if (accountIndex == -1) {
            throw new ItemNotFoundException(String.format(
                    "%s: %s, %s", "Аккаунт с реквизитами", requisites, "не найден.")
            );
        }
        return accountIndex;
    }

    /**
     * Добавляет аккаунт к пользователю.
     * @param passport паспорт пользователя.
     * @param account аккаунт, который добавляем.
     * @throws DuplicateItemException в случае, если реквизиты аккаунтов, добавляемого и существующих совпадают.
     * @throws ItemNotFoundException в случае, если пользователь с заданнм паспортом не найден в хранилище.
     */
    public void addAccountToUser(String passport, Account account)
            throws DuplicateItemException, ItemNotFoundException {
        for (List<Account> checkedAccountList : bankMap.values()) {
            for (Account checkedAccount : checkedAccountList) {
                if (checkedAccount.getRequisites().equals(account.getRequisites())) {
                    throw new DuplicateItemException(String.format(
                            "%s: %s %s", "Аккаунт с реквизитами", account.getRequisites(), "уже существует")
                    );
                }
            }
        }
        getUserAccounts(passport).add(account);
    }

    /**
     * Удаляет аккаунт у пользователя.
     * @param passport паспорт пользователя.
     * @param account аккаунт для удаления.
     * @throws ItemNotFoundException В случае если аккаунт, либо пользователь не найден.
     */
    public void deleteAccountFromUser(String passport, Account account) throws ItemNotFoundException {
        getUserAccounts(passport).remove(account);
    }

    /**
     * Возвращает Лист всех аккаунтов пользователя.
     * @param passport паспорт пользователя.
     * @return Лист всех аккаунтов пользователя.
     * @throws ItemNotFoundException в случае, если пользователь не найден.
     */
    public List<Account> getUserAccounts(String passport) throws ItemNotFoundException {
        Set<Map.Entry<User, List<Account>>> setBankMap = bankMap.entrySet();
        List<Account> findingUser = null;
        for (Map.Entry<User, List<Account>> element : setBankMap) {
            if (element.getKey().getPassport().equals(passport)) {
                findingUser = element.getValue();
                break;
            }
        }
        if (findingUser == null) {
            throw new ItemNotFoundException("Пользователь не найден");
        }
        return findingUser;
    }

    /**
     * Переводит деньги с одного аккаунта на другой.
     * @param srcPassport паспорт переводящего деньги.
     * @param srcRequisite реквизиты аккаунта переводящего деньги.
     * @param destPassport паспорт, того, кому переводят деньги.
     * @param destRequisite реквизиты, того аккаунта, на который переводят деньги.
     * @param amount количество денег для перевода.
     * @return true или false в случае успеха или провала перевода.
     * @throws NotEnoughMoneyException в случае недостатка денег на аккаунте с которого переводят.
     * @throws ItemNotFoundException в случае если пользователь, либо аккаунт не найдены.
     */
    public boolean transferMoney(
            String srcPassport, String srcRequisite, String destPassport, String destRequisite, double amount)
            throws NotEnoughMoneyException, ItemNotFoundException {
        boolean doneTransfer = false;
        if (amount > 0) {
            List<Account> srcAccountList = getUserAccounts(srcPassport);
            int srcAccountIndex = findAccountIndex(srcPassport, srcRequisite);
            if (srcAccountList.get(srcAccountIndex).getValue() >= amount) {
                srcAccountList.get(srcAccountIndex).changeValue(-amount);
                getUserAccounts(destPassport).get(findAccountIndex(destPassport, destRequisite)).changeValue(amount);
                doneTransfer = true;
            } else {
                throw new NotEnoughMoneyException("Недостаточно денег для перевода.");
            }
        }
        return doneTransfer;
    }
}
