package ru.job4j.synchro.withsemaphore;

import java.util.concurrent.Semaphore;

/**
 * Реализация поставщика и потребителя на основе Semaphore.
 * Сохранен отладочный код.
 *
 * @author oywayten (cpc1251@mail.ru)
 * @version 1.0
 */
public class Q {
    static Semaphore semCon = new Semaphore(0);
    static Semaphore semProd = new Semaphore(1);
    int n;

    void get() {
        try {
            System.out.println("semCon get() " + semCon.availablePermits());
            semCon.acquire();
            System.out.println("semCon get() " + semCon.availablePermits());
        } catch (InterruptedException e) {
            System.out.println("Перехвачено исключение типа InterruptedException");
            e.printStackTrace();
        }
        System.out.println("Получено: " + n);
        System.out.println("semProd get() " + semProd.availablePermits());
        semProd.release();
        System.out.println("semProd get() " + semProd.availablePermits());
    }

    void put(int n) {
        try {
            System.out.println("semProd put() " + semProd.availablePermits());
            semProd.acquire();
            System.out.println("semProd put() " + semProd.availablePermits());
        } catch (InterruptedException e) {
            System.out.println("Перехвачено исключение типа InterruptedException");
            e.printStackTrace();
        }
        this.n = n;
        System.out.println("Отправлено: " + n);
        System.out.println("semCon put() " + semCon.availablePermits());
        semCon.release();
        System.out.println("semCon put() " + semCon.availablePermits());
    }
}

class Producer implements Runnable {
    Q q;

    Producer(Q q) {
        this.q = q;
        new Thread(this, "Producer").start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            q.put(i);
        }
    }
}

class Consumer implements Runnable {
    Q q;

    Consumer(Q q) {
        this.q = q;
        new Thread(this, "Consumer").start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            q.get();
        }
    }
}

class ProdCon {
    public static void main(String[] args) {
        Q q = new Q();
        new Consumer(q);
        new Producer(q);
    }
}