package ru.job4j.convert;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 0.1
 */
public class ConvertList {
    /**
     * Конвертирует двухмерный массив в ArrayList
     * @param array заданный для конвертации массив
     * @return ArrayList.
     */
    public List<Integer> toList(int[][] array) {
        ArrayList<Integer> result = new ArrayList<>(array[0].length + array[1].length);
        for (int[] currentArr : array) {
            for (int value : currentArr) {
                result.add(value);
            }
        }
        return result;
    }

    /**
     * Конвертирует заданный ArrayList в двухмерный массив
     * с равномерным разбитием по строкам.
     * @param list Заданный ArrayList.
     * @param rows Требуемое количество строк.
     * @return двухмерный массив.
     */
    public int[][] toArray(List<Integer> list, int rows) {
        int rowLength = (int) Math.ceil(list.size() / (double) rows);
        int[][] result = new int[rows][rowLength];
        int rowsNumber = 0;
        int rowsPosition = 0;
        for (int element : list) {
            if (rowsPosition < rowLength) {
                result[rowsNumber][rowsPosition++] = element;
            } else if (rowsNumber < rows - 1) {
                rowsPosition = 0;
                result[++rowsNumber][rowsPosition++] = element;
            }
        }
        if (list.size() % rows != 0) {
            while (rowsPosition < rowLength) {
                result[rows - 1][rowsPosition++] = 0;
            }
        }
        return result;
    }

    /**
     * Проходит по всем элементам всех массивов в списке list
     * и добавляет их в один общий лист Integer.
     * @param list
     * @return
     */
    public List<Integer> convert (List<int[]> list) {
        List<Integer> result = new ArrayList<>();
        for(int[] array : list) {
            for(int element : array) {
                result.add(element);
            }
        }
        return result;
    }
}
