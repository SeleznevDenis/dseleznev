package ru.job4j;

import java.util.Arrays;

/**
 * @author Denis Selezneww (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class CoffeeMachine {
    /**
     * Метод вычисляет минимальное количество монет для сдачи.
     * @param value Купюра отданная кофеавтомату.
     * @param price Цена кофе.
     * @return Целочисленный массив, который содержит перечисление монет и их номиналов,
     * необходимых вернуть в качестве сдачи.
     */
    public int[] changes(int value, int price) {
        int maxBankNote = 5000;
        int[] coins = {10, 5, 2, 1};
        int[] changes = new int[maxBankNote / coins[0] + coins.length - 1];
        int coinsPosition = 0;
        int changesPosition = 0;
        if (value >= price) {
            int change = value - price;
            while (change > 0) {
                if (change >= coins[coinsPosition]) {
                    change = change - coins[coinsPosition];
                    changes[changesPosition] = coins[coinsPosition];
                    changesPosition++;
                } else {
                    coinsPosition++;
                }
            }
        }
        return Arrays.copyOf(changes, changesPosition);
    }
}
