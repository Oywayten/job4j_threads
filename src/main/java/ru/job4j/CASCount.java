package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Класс неблокирующего счетчика
 */
@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        Integer oldInt;
        int newInt;
        do {
            oldInt = count.get();
            newInt = oldInt + 1;
        } while (!count.compareAndSet(oldInt, newInt));
    }

    public int get() {
        return count.get();
    }
}