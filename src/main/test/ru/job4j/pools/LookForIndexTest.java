package ru.job4j.pools;

import org.junit.jupiter.api.Test;
import ru.job4j.email.User;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тестовый класс для LookForIndex.
 *
 * @author oywayten (cpc1251@mail.ru)
 * @version 1.1, 11.09.2022
 */
class LookForIndexTest {

    @Test
    void whenArrLength3AndResultIndexIs1InMiddle() {
        User[] users = new User[3];
        users[0] = new User("Ivan0", "0@mail.ru");
        users[1] = new User("Ivan1", "1@mail.ru");
        users[2] = new User("Ivan2", "2@mail.ru");
        int result = LookForIndex.lookForIndex(users, new User("Ivan1", "1@mail.ru"));
        assertThat(result).isEqualTo(1);
    }

    @Test
    void whenArrLength3AndIndexNotFoundResultIsMinus1() {
        User[] users = new User[3];
        users[0] = new User("Ivan0", "0@mail.ru");
        users[1] = new User("Ivan1", "1@mail.ru");
        users[2] = new User("Ivan2", "2@mail.ru");
        int result = LookForIndex.lookForIndex(users, new User("Ivan4", "4@mail.ru"));
        assertThat(result).isEqualTo(-1);
    }

    @Test
    void whenArrLength20AndResultIndexIs19InTheEnd() {
        User[] users = {
                new User("Ivan0", "0@mail.ru"),
                new User("Ivan1", "1@mail.ru"),
                new User("Ivan2", "2@mail.ru"),
                new User("Ivan3", "3@mail.ru"),
                new User("Ivan4", "4@mail.ru"),
                new User("Ivan5", "5@mail.ru"),
                new User("Ivan6", "6@mail.ru"),
                new User("Ivan7", "7@mail.ru"),
                new User("Ivan8", "8@mail.ru"),
                new User("Ivan9", "9@mail.ru"),
                new User("Ivan10", "10@mail.ru"),
                new User("Ivan11", "11@mail.ru"),
                new User("Ivan12", "12@mail.ru"),
                new User("Ivan13", "13@mail.ru"),
                new User("Ivan14", "14@mail.ru"),
                new User("Ivan15", "15@mail.ru"),
                new User("Ivan16", "16@mail.ru"),
                new User("Ivan17", "17@mail.ru"),
                new User("Ivan18", "18@mail.ru"),
                new User("Ivan19", "19@mail.ru")
        };
        int result = LookForIndex.lookForIndex(users, new User("Ivan19", "19@mail.ru"));
        assertThat(result).isEqualTo(19);
    }

    @Test
    void whenArrLength3WithNullAndLookForNullResultIs2() {
        User[] users = new User[3];
        users[0] = new User("Ivan0", "0@mail.ru");
        users[1] = new User("Ivan1", "1@mail.ru");
        users[2] = null;
        int result = LookForIndex.lookForIndex(users, null);
        assertThat(result).isEqualTo(2);
    }

    @Test
    void whenArrLength3WithNullAndLookForIvan4ResultIsMinus1() {
        User[] users = new User[3];
        users[0] = new User("Ivan0", "0@mail.ru");
        users[1] = new User("Ivan1", "1@mail.ru");
        users[2] = null;
        int result = LookForIndex.lookForIndex(users, new User("Ivan4", "4@mail.ru"));
        assertThat(result).isEqualTo(-1);
    }

    @Test
    void whenArrLength3WithoutNullAndLookForNullResultIsMinus1() {
        User[] users = new User[3];
        users[0] = new User("Ivan0", "0@mail.ru");
        users[1] = new User("Ivan1", "1@mail.ru");
        users[2] = new User("Ivan2", "2@mail.ru");
        int result = LookForIndex.lookForIndex(users, null);
        assertThat(result).isEqualTo(-1);
    }
}