package ru.job4j.concurrent;

/**
 * Класс симулирует процесс загрузки с помощью Thread.sleep()
 */
public class Wget {
    public static void main(String[] args) {
        Thread first = new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
                System.out.print("\rLoading: " + i + "%");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        first.start();
    }
}
