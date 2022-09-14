package ru.job4j.synchro.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Класс демонстрирует применение класса CyclicBarrier.
 *
 * @author oywayten (cpc1251@mail.ru)
 * @version 1.0
 */
public class BarDemo {
    public static void main(String[] args) {
        CyclicBarrier cb = new CyclicBarrier(3, new BarAction());
        System.out.println("Запуск потоков");
        new Thread(new MyThread(cb, "A")).start();
        new Thread(new MyThread(cb, "B")).start();
        new Thread(new MyThread(cb, "C")).start();
    }
}

/**
 * Поток исполнения использующий барьер типа CyclicBarrier.
 */
class MyThread implements Runnable {
    CyclicBarrier cbar;
    String name;

    MyThread(CyclicBarrier c, String n) {
        cbar = c;
        name = n;
    }

    /**
     * Метод выводит переданное name в консоль и останавливает поток у барьера.
     * После возобновления потока - выводит порядок, в котором потоки достигают барьерной точки. 0 - последний поток.
     */
    @Override
    public void run() {
        System.out.println(name);
        try {
            int await = cbar.await();
            System.out.println(name + " " + await);
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

/**
 * Объект этого класса вызывается по достижении барьера типа CyclicBarrier и выводит в консоль сообщение.
 */
class BarAction implements Runnable {

    @Override
    public void run() {
        System.out.println("Барьер достигнут");
    }
}

