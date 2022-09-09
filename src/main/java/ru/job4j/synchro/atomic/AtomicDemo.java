package ru.job4j.synchro.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Реализация синхронизации на основе атомарных операций.
 *
 * @author oywayten (cpc1251@mail.ru)
 * @version 1.0, 05.09.2022
 */
public class AtomicDemo {
    /**
     * Метод демонстрирует работу атомарных операций.
     * Запуск потоков производится как отдельно через {@link Thread},
     * так и через пул потоков {@link ExecutorService}.
     *
     * @param args параметры командной строки (не используются)
     */
    public static void main(String[] args) {
        new Thread(new AtomThread("A")).start();
        new Thread(new AtomThread("B")).start();
        new Thread(new AtomThread("C")).start();
        ExecutorService ex = Executors.newFixedThreadPool(2);
        ex.execute(new AtomThread("A"));
        ex.execute(new AtomThread("B"));
        ex.execute(new AtomThread("C"));
        ex.shutdown();
    }
}

/**
 * Общий ресурс со счетчиком.
 */
class Shared {
    static AtomicInteger ai = new AtomicInteger(0);
}

/**
 * Поток исполнения в котором инкрементируется занчение счетчика.
 */
class AtomThread implements Runnable {
    String name;

    public AtomThread(String name) {
        this.name = name;
    }

    /**
     * Метод беред значение общего ресурса и делает приращение.
     * Благодаря атомарности операции {@link AtomicInteger#getAndIncrement()}
     * счетчик прирастает корректно при работе нескольких потоков одновременно,
     * так как операции атомарны.
     */
    @Override
    public void run() {
        System.out.println("Запуск потока " + name);
        for (int i = 0; i < 5; i++) {
            System.out.println("Поток " + name + " получено: "
                    + Shared.ai.getAndIncrement());
        }
    }
}