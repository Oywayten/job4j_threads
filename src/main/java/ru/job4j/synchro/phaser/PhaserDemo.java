package ru.job4j.synchro.phaser;

import java.util.concurrent.Phaser;

/**
 * Пример применения класса Рhaser.
 *
 * @author oywayten (cpc1251@mail.ru)
 * @version 1.0, 04.09.2022
 */
public class PhaserDemo {
    public static void main(String[] args) {
        Phaser phsr = new Phaser(1);
        int curPhase;
        System.out.println("Запуск потоков");
        new MyThread(phsr, "A");
        new MyThread(phsr, "B");
        new MyThread(phsr, "C");
        curPhase = phsr.getPhase();
        phsr.arriveAndAwaitAdvance();
        System.out.println("Фаза " + curPhase + " завершена");
        curPhase = phsr.getPhase();
        phsr.arriveAndAwaitAdvance();
        System.out.println("Фаза " + curPhase + " завершена");
        curPhase = phsr.getPhase();
        phsr.arriveAndAwaitAdvance();
        System.out.println("Фаза " + curPhase + " завершена");
        phsr.arriveAndDeregister();
        if (phsr.isTerminated()) {
            System.out.println("Синхронизатор фаз завершен");
        }
    }
}

/**
 * Поток исполнения, применяющий синхронизатор фаз
 * типа Phaser
 */
class MyThread implements Runnable {
    Phaser ph;
    String name;

    public MyThread(Phaser ph, String name) {
        this.ph = ph;
        this.name = name;
        ph.register();
        new Thread(this).start();
    }

    /**
     * Переопределенный метод run выполняет код и
     * синхронизирует работу потоков в фазах
     * с помощью метода {@link Phaser#arriveAndAwaitAdvance()} ;
     */
    @Override
    public void run() {
        System.out.println("Поток " + name + " начинает первую фазу");
        ph.arriveAndAwaitAdvance();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Поток " + name + " начинает вторую фазу");
        ph.arriveAndAwaitAdvance();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Поток " + name + " начинает третью фазу");
        ph.arriveAndDeregister();
    }
}
