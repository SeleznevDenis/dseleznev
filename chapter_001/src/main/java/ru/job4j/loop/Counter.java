package ru.job4j.loop;

public class Counter {
    public int add(int start, int finish) {
        int count = 0;
      for (int i = start; i <= finish; i++) {
          if (i % 2 != 0) {
              continue;
          }
          count += i;
      }
      return count;
    }
}
