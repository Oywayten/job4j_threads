package ru.job4j.email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    public static final int SLEEP_TIME = 100;
    private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    /**
     * Метод отправляет почту через {@code pool}
     * Берет данные пользователя и подставляет в шаблон
     *
     * @param user объект User с данными пользователя для отправки
     */
    private void emailTo(User user) {
        pool.submit(() -> send(
                String.format("Notification %s to email %s.", user.getUsername(), user.getEmail()),
                String.format("Add a new event to %s", user.getUsername()),
                user.getEmail()));
    }

    /**
     * Метод закрывает pool
     */
    public void close() throws InterruptedException {
        pool.shutdown();
        while (!pool.isTerminated()) {
            Thread.sleep(SLEEP_TIME);
        }
    }

    /**
     * Метод для отправки сообщений
     *
     * @param subject тема сообщения
     * @param body    тело сообщения
     * @param email   электронный адрес для отправки сообщения
     */
    public void send(String subject, String body, String email) {

    }
}
