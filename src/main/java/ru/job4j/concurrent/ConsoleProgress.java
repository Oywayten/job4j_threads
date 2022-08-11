package ru.job4j.concurrent;

/**
 * Класс для вывода процесса загрузки в консоль
 */
public class ConsoleProgress implements java.lang.Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(1000);
        progress.interrupt();
    }

    @Override
    public void run() {
        char[] process = {'\\', '|', '/'};
        int count = 0;
        System.out.print("Loading ... |.");
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("\r load: " + process[count++]);
                if (count == 3) {
                    count = 0;
                }
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

