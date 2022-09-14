package ru.job4j.synchro.wrong;

/**
 * Класс синхронизируемой очереди. Весь код файла демонстрирует неправильную реализацию поставщика и потребителя
 *
 * @author oywayten (cpc1251@mail.ru)
 * @version 1.0
 */
public class Q {
    int n;

    synchronized int getN() {
        System.out.println("Message received " + n);
        return n;
    }

    synchronized void putN(int n) {
        this.n = n;
        System.out.println("Message sent " + n);
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