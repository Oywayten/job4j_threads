package ru.job4j.deadlock;

class A {
    synchronized void foo(B b) {
        String name = Thread.currentThread().getName();
        System.out.println(name + " entered the method A.foo()");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Class A interrupted");
            e.printStackTrace();
        }
        System.out.println(name + " trying to call method B.last()");
        b.last();
    }

    synchronized void last() {
        System.out.println("In method B.last()");
    }
}

class B {
    synchronized void bar(A a) {
        String name = Thread.currentThread().getName();
        System.out.println(name + " entered B.bar()");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Class B interrupted");
            e.printStackTrace();
        }
        System.out.println(name + " trying to call method A.last()");
        a.last();
    }

    synchronized void last() {
        System.out.println("In method A.last()");
    }
}

public class Deadlock implements Runnable {
    A a = new A();
    B b = new B();

    Deadlock() {
        Thread.currentThread().setName("Main thread");
        Thread t = new Thread(this, "Concurrent thread");
        t.start();
        a.foo(b);
        System.out.println("Back to main thread");
    }

    public static void main(String[] args) {
        new Deadlock();
    }

    @Override
    public void run() {
        b.bar(a);
        System.out.println("Back to concurrent thread");
    }
}