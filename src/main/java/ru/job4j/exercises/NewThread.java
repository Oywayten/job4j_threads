package ru.job4j.exercises;

public class NewThread implements Runnable {
    String name;
    Thread t;

    NewThread(String threadName) {
        name = threadName;
        t = new Thread(this, name);
        System.out.println("new thread " + t);
        t.start();
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println(name + ": " + i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println(name + " interrupt");
            e.printStackTrace();
        }
        System.out.println(name + " ended");
    }
}

class MultyThreadDemo {
    public static void main(String[] args) {
        NewThread one = new NewThread("One");
        one.t.setPriority(Thread.NORM_PRIORITY);
        System.out.println("one " + one.t.getPriority());
        NewThread two = new NewThread("Two");
        two.t.setPriority(Thread.MIN_PRIORITY);
        System.out.println("two " + two.t.getPriority());
        NewThread three = new NewThread("Three");
        three.t.setPriority(Thread.MAX_PRIORITY);
        System.out.println("three " + three.t.getPriority());
        System.out.println(one.t.isAlive());
        System.out.println(two.t.isAlive());
        System.out.println(three.t.isAlive());
        try {
            one.t.join();
            two.t.join();
            three.t.join();
        } catch (InterruptedException e) {
            System.out.println("Main Thread is interrupt");
            e.printStackTrace();
        }
        System.out.println("Main Thread is ended");
    }
}