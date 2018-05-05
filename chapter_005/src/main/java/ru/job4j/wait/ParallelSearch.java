package ru.job4j.wait;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * Ищет пути файлов из заданной дирректории и поддиректориях,
 * содержащих заданный текст и соответствующих требуемому расширению.
 * Работает параллельно, одна нить находит пути файлов с требуемым расширением.
 * Другая нить проверяет найденные файлы, на содержание заданного текста.
 * @author Denis Seleznev.
 * @version $Id$
 * @since 05.05.2018
 */
@ThreadSafe
public class ParallelSearch {

    /**
     * Хранит путь к корневой директории для поиска.
     */
    private final String root;

    /**
     * Хранит текст для поиска.
     */
    private final String text;

    /**
     * Хранит список расширений файлов для поиска.
     */
    private final List<String> exts;

    /**
     * Флаг остановки нити read.
     */
    private volatile boolean finish;

    /**
     * Очередь, содержащая пути к файлам соответствующим заданным расширениям.
     */
    @GuardedBy("this")
    private final Queue<String> files = new LinkedList<>();

    /**
     * Список содержащий пути к файлам соответствующим
     * заданному расширению и содержащим заданный текст.
     */
    @GuardedBy("this")
    private final List<String> paths = new ArrayList<>();

    /**
     * Ининициализирует критерии поиска.
     * @param root заданный для поиска корень дирректории.
     * @param text заданный для поиска текст.
     * @param exts список, содержащий заданные для поиска расширения файлов.
     */
    public ParallelSearch(String root, String text, List<String> exts) {
        this.root = root;
        this.text = text;
        this.exts = exts;
    }

    /**
     * Внутренний класс, описывающий объект,
     * который рекурсивно производит посещение
     * всех поддиректорий под заданной, включительно.
     */
    private class Finder extends SimpleFileVisitor<Path> {

        /**
         * Посещает дирректорию, проверяет оканчивается ли имя файла, заданным расширением.
         * В случае совпадения, добавляет путь файла в очередь files.
         * @param file дирректория для посещения.
         */
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
            if (file != null) {
                String fileName = file.getFileName().toString();
                for (String ext : exts) {
                    if (fileName.endsWith(ext)) {
                        synchronized (ParallelSearch.this) {
                            files.add(file.toString());
                            ParallelSearch.this.notify();
                        }
                        break;
                    }
                }
            }
            return CONTINUE;
        }
    }

    /**
     * Запускает нити, для работы, затем дожидается завершения их работы.
     * Нить search производит поиск файлов соответствующих заданному расширению
     * и добавляет файлы в очередь files.
     * Нить read читает файл проверяет содержит ли он text и если содержит,
     * то добавляет путь файла в paths.
     */
    public void init() {
        Thread search = new Thread(() -> {
            try {
                Files.walkFileTree(Paths.get(root), new Finder());
            } catch (IOException e) {
                e.printStackTrace();
            }
            synchronized (ParallelSearch.this) {
                finish = true;
                ParallelSearch.this.notify();
            }

        });
        Thread read = new Thread() {
            private String currentFile;
            private boolean filesIsEmpty;

            /**
             * Работает в цикле по 2м условиям finish и filesIsEmpty,
             * При выставлении флага finish = true, дожидается когда очередь опустеет окончательно
             * и только после этого прекращает работу. В случае отсутствии файлов в очереди при флаге
             * finish = false, ждет. При наличии файлов в очереди, изымает их из неё для последующей обработки.
             */
            @Override
            public void run() {
                while (!finish || !filesIsEmpty) {
                    synchronized (ParallelSearch.this) {
                        if (files.isEmpty()) {
                            try {
                                ParallelSearch.this.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (finish && files.isEmpty()) {
                            break;
                        }
                        currentFile = files.poll();
                        ParallelSearch.this.notify();
                    }
                    try {
                        parsingCurrentFile();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    synchronized (ParallelSearch.this) {
                        this.filesIsEmpty = files.isEmpty();
                    }
                }
            }

            /**
             * Производит чтение файла и добавляет его путь в
             * paths в случае если файл содержит заданный text.
             */
            private void parsingCurrentFile() throws FileNotFoundException {
                Scanner fileScanner = new Scanner(new File(currentFile));
                while (fileScanner.hasNext()) {
                    if (fileScanner.nextLine().contains(text)) {
                        synchronized (ParallelSearch.this) {
                            paths.add(currentFile);
                            break;
                        }
                    }
                }
            }
        };
        search.start();
        read.start();
        try {
            search.join();
            read.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return найденные пути файлов, соответствующих заданным критериям.
     */
    synchronized List<String> result() {
        return this.paths;
    }
}
