package ru.job4j.exercises;

import java.util.ArrayList;
import java.util.List;

public class SynchroMetod {
    private static final List<Double> NUMBERS = new ArrayList<>();

    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(SynchroMetod::someHeavyMethod));
            threads.add(new Thread(SynchroMetod::someHeavyMethod2));
        }
        threads.forEach(Thread::start);
    }

    private static void someHeavyMethod2() {
        synchronized (SynchroMetod.class) {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " is interrupt");
                    e.printStackTrace();
                }
            }
        }
    }

    public static synchronized void someHeavyMethod() {
        for (int i = 0; i < 1000_000; i++) {
            double res = Math.random() / Math.random();
            synchronized (NUMBERS) {
                NUMBERS.add(res);
            }
        }
        System.out.println(Thread.currentThread().getName() + " " + NUMBERS.size());
        NUMBERS.clear();
    }
}
