package ru.job4j.email;

/**
 * Модель данных адресата сообщений.
 *
 * @author oywayten (cpc1251@mail.ru)
 * @version 1.0, 09.09.2022
 */
public class User {
    private final String username;
    private final String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
