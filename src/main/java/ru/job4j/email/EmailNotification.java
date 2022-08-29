package ru.job4j.email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    public static final int SLEEP_TIME = 100;
    private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private String subject;
    private String body;

    /**
     * Конструктор принимает пользователя и создает задачу,
     * которая будет создавать данные для пользователя и передавать их в метод send
     */
    public EmailNotification() {
        pool.submit(() -> {

        });
    }

    /**
     * Метод отправляет почту через ExecutorService
     * Берет данные пользователя и подставляет в шаблон
     *
     * @param user объект User с данными пользователя для отправки
     */
    private void emailTo(User user) {
        subject = String.format("Notification %s to email %s.", user.username, user.email);
        body = String.format("Add a new event to %s", user.username);
        send(subject, body, user.email);
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

    public void send(String subject, String body, String email) {

    }

    /**
     * Модель User описывают поля username, email
     */
    static class User {
        private final String username;
        private final String email;

        public User(String username, String email) {
            this.username = username;
            this.email = email;
        }
    }
}
