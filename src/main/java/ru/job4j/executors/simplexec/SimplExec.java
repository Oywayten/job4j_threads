package ru.job4j.executors.simplexec;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Класс применяет исполнителя для создания пула 2 потоков и
 * выполнения 4 задач.
 *
 * @author oywayten (cpc1251@mail.ru)
 * @version 1.0, 04.09.2022
 */
public class SimplExec {
    public static void main(String[] args) {
        CountDownLatch cd1 = new CountDownLatch(5);
        CountDownLatch cd2 = new CountDownLatch(5);
        CountDownLatch cd3 = new CountDownLatch(5);
        CountDownLatch cd4 = new CountDownLatch(5);
        ExecutorService ex = Executors.newFixedThreadPool(2);
        System.out.println("Запуск потоков");
        ex.execute(new MyThread(cd1, "A"));
        ex.execute(new MyThread(cd2, "B"));
        ex.execute(new MyThread(cd3, "C"));
        ex.execute(new MyThread(cd4, "D"));
        try {
            cd1.await();
            cd2.await();
            cd3.await();
            cd4.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ex.shutdown();
        System.out.println("Завершение потоков");
    }
}

class MyThread implements Runnable {
    CountDownLatch latch;
    String name;

    public MyThread(CountDownLatch latch, String name) {
        this.latch = latch;
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(name + ": " + i);
            latch.countDown();
        }
    }
}
