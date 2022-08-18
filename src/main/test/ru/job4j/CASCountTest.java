package ru.job4j;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CASCountTest {
    static CASCount count = new CASCount();

    @BeforeAll
    public static void setup() {
        for (int i = 0; i < 128; i++) {
            count.increment();
        }
        System.out.println("test1");
        System.out.println(count.get());
    }

    @Test
    public void whenCountMoreThen128IsFrom129To132() {
        System.out.println("test2");
        count.increment();
        assertThat(count.get()).isEqualTo(129);
        count.increment();
        assertThat(count.get()).isEqualTo(130);
        count.increment();
        assertThat(count.get()).isEqualTo(131);
        count.increment();
        assertThat(count.get()).isEqualTo(132);
    }
}