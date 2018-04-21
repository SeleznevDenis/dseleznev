package ru.job4j.count;

import sun.applet.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Set;

/**
 * WordIndex.
 *
 * @author Denis Seleznev (d.selezneww@mail.ru)
 * @version $Id$
 * @since 21.04.2018
 */
public class WordIndex {

    /**
     * Модифицированное префиксное дерево для хранения слов и их индексов.
     */
    private Trie storage = new Trie();

    /**
     * Производит загрузку из файла в хранилище. Удаляет знаки препинания.
     *
     * @param fileName путь к файлу, для добавления.
     * @throws FileNotFoundException в случае, если файл не найден.
     */
    public void loadFile(String fileName) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File(fileName));
        int wordCounter = 0;
        while (fileScanner.hasNext()) {
            wordCounter++;
            storage.put(fileScanner.next().replaceAll("\\p{Punct}", ""), wordCounter);
        }
    }

    /**
     * Ищет слово в главном хранилище и возвращает его индексы.
     *
     * @param word заданное для поиска слово.
     * @return множество индексов слова по порядку возрастания,
     * либо пустое множество если слова нет в хранилище.
     */
    public Set<Integer> getIndicesForWord(String word) {
        return this.storage.getIndices(word);
    }
}
