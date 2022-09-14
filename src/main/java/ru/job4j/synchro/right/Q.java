package ru.job4j.synchro.right;

/**
 * Класс синхронизируемой очереди.
 * Весь код файла демонстрирует неправильную реализацию поставщика и потребителя.
 *
 * @author oywayten (cpc1251@mail.ru)
 * @version 1.0
 */
public class Q {
    int n;
    boolean valueSet;

    synchronized int getN() {
        while (!valueSet) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException in " + Thread.currentThread().getName());
                e.printStackTrace();
            }
        }
        System.out.println("Message received " + n);
        valueSet = false;
        notify();
        return n;
    }

    synchronized void putN(int n) {
        while (valueSet) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException in" + Thread.currentThread().getName());
                e.printStackTrace();
            }
        }
        this.n = n;
        valueSet = true;
        System.out.println("Message sent " + n);
        notify();
    }
}

class Producer implements Runnable {
    Q q;

    Producer(Q q) {
        this.q = q;
        new Thread(this, "Supplier").start();
    }

    @Override
    public void run() {
        int i = 0;
        while (true) {
            q.putN(i);
            i++;
        }
    }
}

class Consumer implements Runnable {
    Q q;

    Consumer(Q q) {
        this.q = q;
        new Thread(this, "Consumer").start();
    }

    @Override
    public void run() {
        while (true) {
            q.getN();
        }
    }
}

class PC {
    public static void main(String[] args) {
        Q q = new Q();
        System.out.println("For stop press Ctrl+C");
        new Consumer(q);
        new Producer(q);

    }
}