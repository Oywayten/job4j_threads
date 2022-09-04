package ru.job4j.synchro.phaser;

import java.util.concurrent.Phaser;

/**
 * Класс демонстрирует наследование от Phaser и
 * переопределение метода {@link Phaser#onAdvance(int, int)} с целью
 * выполнения только определенного количества фаз.
 *
 * @author oywayten (cpc1251@mail.ru)
 * @version 1.0, 04.09.2022
 */
public class MyPhaser extends Phaser {
    /**
     * Заданное количество фаз выполнения.
     */
    int numPhases;

    /**
     * Конструктор класса MyPhaser регистрирует стороны и
     * задает количество фаз для выполнения.
     *
     * @param parties    количество зарегистрированных сторон
     * @param phaseCount количество фаз для выполнения
     */
    public MyPhaser(int parties, int phaseCount) {
        super(parties);
        this.numPhases = phaseCount - 1;
    }

    @Override
    protected boolean onAdvance(int curPhaseNumber, int registeredParties) {
        System.out.println("Фаза " + curPhaseNumber + " завершена.\n");
        return curPhaseNumber == numPhases || registeredParties == 0;
    }
}

class PhaserDemo2 {
    public static void main(String[] args) {
        MyPhaser phsr = new MyPhaser(1, 5);
        System.out.println("Запуск потоков\n");
        new MyThread2(phsr, "A");
        new MyThread2(phsr, "B");
        new MyThread2(phsr, "C");
        while (!phsr.isTerminated()) {
            phsr.arriveAndAwaitAdvance();
        }
        System.out.println("Синхронизатор фаз завершен");
    }
}

class MyThread2 implements Runnable {
    Phaser phsr;
    String name;

    public MyThread2(Phaser phsr, String name) {
        this.phsr = phsr;
        this.name = name;
        phsr.register();
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (!phsr.isTerminated()) {
            System.out.println("Поток " + name + " начинает фазу " + phsr.getPhase());
            phsr.arriveAndAwaitAdvance();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}