package ru.job4j.exercises.multithreading;

public class MyRunnable implements Runnable {
    @Override
    public void run() {
        methodOne();
    }

    private void methodOne() {
        int localVariable1 = 45;
        MySharedObject localVariable2 = MySharedObject.sharedInstance;
        methodTwo();
    }

    private void methodTwo() {
        Integer localVariable1 = 99;
    }
}
