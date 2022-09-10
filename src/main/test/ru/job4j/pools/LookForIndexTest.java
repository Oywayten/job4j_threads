package ru.job4j.pools;

import org.junit.jupiter.api.Test;
import ru.job4j.email.User;

import java.util.concurrent.ForkJoinPool;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тестовый класс для LookForIndex.
 *
 * @author oywayten (cpc1251@mail.ru)
 * @version 1.0, 10.09.2022
 */
class LookForIndexTest {

    @Test
    void whenIndex1() {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        User[] users = new User[3];
        users[0] = new User("Ivan0", "0@mail.ru");
        users[1] = new User("Ivan1", "1@mail.ru");
        users[2] = new User("Ivan2", "2@mail.ru");
        int result = LookForIndex.lookForIndex(users, new User("Ivan1", "1@mail.ru"));
        assertThat(result).isEqualTo(1);
    }

    @Test
    void whenNotFound() {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        User[] users = new User[3];
        users[0] = new User("Ivan0", "0@mail.ru");
        users[1] = new User("Ivan1", "1@mail.ru");
        users[2] = new User("Ivan2", "2@mail.ru");
        int result = LookForIndex.lookForIndex(users, new User("Ivan4", "4@mail.ru"));
        assertThat(result).isEqualTo(-1);
    }
}