package ru.job4j.concurrent;

/**
 * Класс для вывода процесса загрузки в консоль
 */
public class ConsoleProgress implements java.lang.Runnable {
    private static final int ZERO = 0;
    
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(1000);
        progress.interrupt();
    }

    @Override
    public void run() {
        char[] process = {'\\', '|', '/'};
        int count = ZERO;
        System.out.print("Loading ... |.");
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("\r load: " + process[count++]);
                count = count == process.length ? ZERO : count;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
    }
}

