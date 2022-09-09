package ru.job4j.notyfiwait;

public class NewThread implements Runnable {
    String name;
    Thread t;
    boolean suspendFlag;

    NewThread(String thradName) {
        name = thradName;
        t = new Thread(this, name);
        System.out.println("New Thread: " + name);
        t.start();
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 15; i++) {
                System.out.println(name + ": " + (14 - i));
                Thread.sleep(200);
                synchronized (this) {
                    while (suspendFlag) {
                        wait();
                    }
                }
            }
        } catch (InterruptedException e) {
            System.out.println(name + " interrupted");
            e.printStackTrace();
        }
        System.out.println(name + " ended");
    }

    synchronized void mySuspend() {
        suspendFlag = true;
    }

    synchronized void myresume() {
        suspendFlag = false;
        notify();
    }
}

class SuspendResume {
    public static void main(String[] args) {
        NewThread nt1 = new NewThread("One");
        NewThread nt2 = new NewThread("Two");
        try {
            Thread.sleep(1000);
            nt1.mySuspend();
            System.out.println("Suspend One");
            Thread.sleep(1000);
            nt1.myresume();
            System.out.println("Resume One");
            nt2.mySuspend();
            System.out.println("Suspend Two");
            Thread.sleep(1000);
            nt2.myresume();
            System.out.println("Resume Two");
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted");
            e.printStackTrace();
        }
        System.out.println("Wait threads ended");
        try {
            nt1.t.join();
            nt2.t.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted");
            e.printStackTrace();
        }
        System.out.println("Main thread ended");
    }
}