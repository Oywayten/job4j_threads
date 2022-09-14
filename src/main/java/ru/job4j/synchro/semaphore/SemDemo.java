package ru.job4j.synchro.semaphore;

import java.util.concurrent.Semaphore;

/**
 * Класс для простого примера применения семафора.
 *
 * @author oywayten (cpc1251@mail.ru)
 * @version 1
 */
public class SemDemo {
    public static void main(String[] args) {
        Semaphore sem = new Semaphore(1);
        new IncThread("A", sem);
        new DecThread("B", sem);
    }
}

/**
 * Класс общий ресурс.
 */
class Shared {
    static int count;
}

/**
 * Класс увеличивает значение счетчика на единицу.
 */
class IncThread implements Runnable {
    String name;
    Semaphore sem;

    public IncThread(String name, Semaphore sem) {
        this.name = name;
        this.sem = sem;
        new Thread(this, "IncThread").start();
    }

    @Override
    public void run() {
        System.out.println("Thread " + name + " is started");
        try {
            System.out.println("Thread " + name + " awaiting permission");
            sem.acquire();
            System.out.println("Thread " + name + " gets permission");
            for (int i = 0; i < 5; i++) {
                Shared.count++;
                System.out.println(name + ": " + Shared.count);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread " + name + " releases permission");
        sem.release();
    }
}

/**
 * Класс уменьшает значение счетчика на единицу.
 */
class DecThread implements Runnable {
    String name;
    Semaphore sem;

    public DecThread(String name, Semaphore sem) {
        this.name = name;
        this.sem = sem;
        new Thread(this, "DecThread").start();
    }

    @Override
    public void run() {
        System.out.println("Thread " + name + " is started");
        try {
            System.out.println("Thread " + name + " is awaiting permission");
            sem.acquire();
            System.out.println("Thread " + name + " gets permission");
            for (int i = 0; i < 5; i++) {
                Shared.count--;
                System.out.println(name + ": " + Shared.count);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread " + name + " releases permission");
        sem.release();
    }
}
