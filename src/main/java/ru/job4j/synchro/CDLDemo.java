package ru.job4j.synchro;

import java.util.concurrent.CountDownLatch;

/**
 * Демо примерения класса CountDownLatch.
 *
 * @author oywayten (cpc1251@mail.ru)
 * @version 1.0
 */
public class CDLDemo {
    public static void main(String[] args) {
        CountDownLatch cdl = new CountDownLatch(5);
        System.out.println("Запуск потока исполнения");
        new Thread(new MyThread(cdl)).start();
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Завершение потока исполнения");
    }
}

class MyThread implements Runnable {
    CountDownLatch latch;

    MyThread(CountDownLatch l) {
        latch = l;
    }

    @Override
    public void run() {
        int i = (int) latch.getCount();
        while (i != 0) {
            System.out.println(i);
            latch.countDown();
            i = (int) latch.getCount();
        }
    }
}
