package ru.job4j.synchro;

/**
 * Класс демонстрирует необходимость блоков синхронизации
 *
 * @author oywayten (cpc1251@mail.ru)
 * @version 1.0
 */
public class CallMe {
    /**
     * Метод синхронизирован кючевым словом {@code synchronized} и
     * при вызове выводит в консоль переданное сообщение с квадратной скобкой до и после сообщения
     *
     * @param msg сообщение типа String
     */
    public synchronized void call(String msg) {
        System.out.print("[");
        System.out.print(msg);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("]");
    }
}

/**
 * Класс принимает объект {@link CallMe} и сообщение. И передает сообщение в CallMe в новом созданном потоке
 */
class Caller implements Runnable {
    public final Thread t;
    private final CallMe target;
    private final String msg;

    public Caller(CallMe target, String msg) {
        this.target = target;
        this.msg = msg;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        target.call(msg);
    }
}

/**
 * Класс для демонстрации работы потоков и их синхронизации.
 * Вывод отдельных сообщений синхронизирован ключевым словом {@code synchronized} в {@link CallMe#call(String)},
 * но порядок вывода сообщений разными {@link Caller} всё еще не синхронизирован.
 */
class Synch {
    public static void main(String[] args) {
        CallMe target = new CallMe();
        Caller caller1 = new Caller(target, "Welcome");
        Caller caller2 = new Caller(target, "to syncronized");
        Caller caller3 = new Caller(target, "world!");
        try {
            caller1.t.join();
            caller2.t.join();
            caller3.t.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
            e.printStackTrace();
        }
    }
}