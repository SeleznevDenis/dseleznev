Задача: Есть большой текстовый файл, в котором хранятся различные слова, некоторые из которых – по многу раз.

Нужно сделать класс WordIndex (можно создавать и другие сопутствующие классы, если это необходимо), который по сути будет являться индексом. Он должен позволять по заданному слову находить все вхождения (позиции) его в файле.

 Данный класс должен иметь следующие методы:

public void loadFile(String filename); Загрузка данных из файла и построение индекса.
public  Set<Integer> getIndexes4Word(String searchWord); Возвращает список позиций слова в файле. Если данного слова в файле нет, то возвращается null.
 Для хранения данных в памяти предлагается использовать Trie (префиксное дерево).

 