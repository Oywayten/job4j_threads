package ru.job4j.synchro.locks;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Реализация блокировки на основе Lock.
 *
 * @author oywayten (cpc1251@mail.ru)
 * @version 1.0, 05.09.2022
 */
public class LockDemo {
    /**
     * Демонстрация работы простой блокировки.
     *
     * @param args параметры командной строки (не используются).
     */
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        new Thread(new LockThread(lock, "A")).start();
        new Thread(new LockThread(lock, "B")).start();
    }
}

/**
 * Класс с общим ресурсом - счетчиком.
 */
class Shared {
    /**
     * Счетчик - общий ресурс.
     */
    static int count;
}

/**
 * Класс потока исполнения, инкрементирующий значение счетчика.
 */
class LockThread implements Runnable {
    ReentrantLock lock;
    String name;

    public LockThread(ReentrantLock lock, String name) {
        this.lock = lock;
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("Запуск потока " + name);
        System.out.println("Поток " + name + " ожидает блокировки счетчика");
        lock.lock();
        System.out.println("Поток " + name + " блокирует счетчик");
        Shared.count++;
        System.out.println("Поток " + name + ": " + Shared.count);
        System.out.println("Поток " + name + " ожидает");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Поток " + name + " разблокирует счетчик");
            lock.unlock();
        }
    }
}