package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Класс реализует параллельный поиск индекса в массиве объектов.
 *
 * @author oywayten (cpc1251@mail.ru)
 * @version 1.1, 11.09.2022
 */
public class LookForIndex<T> extends RecursiveTask<Integer> {
    private static final int SEQ_TRESHOLD = 10;
    private final T[] data;
    private final int start;
    private final int end;
    private final T object;

    private LookForIndex(T[] array, T object, int start, int end) {
        this.data = array;
        this.start = start;
        this.end = end;
        this.object = object;
    }

    /**
     * Статический метод для поиска индекса объекта в массиве.
     * Принимает массив, в котором ищем, и объект, для поиска.
     *
     * @param array  массив, в котором ищем
     * @param object объект, который ищем
     * @param <T>    параметризованный тип для метода поиска
     * @return индекс объекта в массиве, либо -1, если объекта нет
     */
    public static <T> int lookForIndex(T[] array, T object) {
        LookForIndex<T> look = new LookForIndex<>(array, object, 0, array.length - 1);
        ForkJoinPool pool = ForkJoinPool.commonPool();
        return pool.invoke(look);
    }

    /**
     * Обобщенный метод поиска индекса в массиве объектов.
     * Если размер массива меньше 10, то используется последовательный поиск.
     */
    @Override
    protected Integer compute() {
        if (end - start < SEQ_TRESHOLD) {
            return sequentialSearch();
        }
        int middle = (end + start) / 2;
        LookForIndex<T> look1 = new LookForIndex<>(data, object, start, middle);
        LookForIndex<T> look2 = new LookForIndex<>(data, object, middle, end);
        look1.fork();
        look2.fork();
        int sum1 = look1.join();
        int sum2 = look2.join();
        return Math.max(sum1, sum2);
    }

    /**
     * Вспомогательный метод для последовательного поиска индекса объекта;
     *
     * @return найденный индекс объекта, или -1, если объект не найден
     */
    private int sequentialSearch() {
        for (int i = start; i <= end; i++) {
            if (data[i].equals(object)) {
                return i;
            }
        }
        return -1;
    }
}