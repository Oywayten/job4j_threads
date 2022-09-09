package ru.job4j.pools;

import ru.job4j.email.User;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Класс реализует параллельный поиск индекса в массиве объектов.
 *
 * @author oywayten (cpc1251@mail.ru)
 * @version 1.0, 10.09.2022
 */
public class LookForIndex<T> extends RecursiveTask<Integer> {
    private static final int SEQ_TRESHOLD = 10;
    private final T[] data;
    private final int start;
    private final int end;
    private final T object;

    public LookForIndex(T[] array, T object, int start, int end) {
        this.data = array;
        this.start = start;
        this.end = end;
        this.object = object;
    }

    /**
     * Метод демонстрирует работу параллельного
     *
     * @param args параметры командной строки (не используются)
     */
    public static void main(String[] args) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        User[] users = new User[100];
        for (int i = 0; i < users.length; i++) {
            users[i] = new User("Ivan" + i, i + "@mail.ru");
        }
        System.out.println(Arrays.toString(users));
        LookForIndex<User> task = new LookForIndex<>(users, new User("Ivan5", "5@mail.ru"), 0, users.length);
        Integer result = pool.invoke(task);
        System.out.println(result);
    }

    /**
     * Обобщенный метод поиска индекса в массиве объектов.
     * Если размер массива меньше 10, то используется последовательный поиск.
     */
    @Override
    protected Integer compute() {
        int index = -1;
        if (end - start < SEQ_TRESHOLD) {
            for (int i = start; i < end; i++) {
                if (data[i].equals(object)) {
                    index = i;
                }
            }
        } else {
            int middle = (end + start) / 2;
            LookForIndex<T> task1 = new LookForIndex<>(data, object, start, middle);
            LookForIndex<T> task2 = new LookForIndex<>(data, object, middle, end);
            task1.fork();
            task2.fork();
            int sum1 = task1.join() != -1 ? task1.join() : 0;
            int sum2 = task2.join() != -1 ? task2.join() : 0;
            index = sum1 + sum2;
        }
        return index;
    }
}