package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final String file;
    private final int speed;
    private static final int OFF = 0;
    private static final int BUF_SIZE = 1024;
    private static final int OUT_OF = -1;

    public Wget(String url, String file, int speed) {
        this.url = url;
        this.file = file;
        this.speed = speed;
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        String file = args[1];
        int speed = Integer.parseInt(args[2]);
        Thread wget = new Thread(new Wget(url, file, speed));
        wget.start();
        wget.join();
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            byte[] dataBuffer = new byte[BUF_SIZE];
            int bytesRead;
            long preReadTime = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, OFF, BUF_SIZE)) != OUT_OF) {
                long postReadTime = System.currentTimeMillis();
                long deltaTime = (postReadTime - preReadTime);
                fileOutputStream.write(dataBuffer, OFF, bytesRead);
                if (deltaTime < speed) {
                    System.out.println(deltaTime);
                    Thread.sleep(speed - deltaTime);
                }
                preReadTime = System.currentTimeMillis();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        long time = System.currentTimeMillis();
    }
}