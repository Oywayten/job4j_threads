package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CacheTest {

    @Test
    public void when2AddSameBase1Update1DeleteAndThenTryUpdateAgain() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        assertThat(cache.add(base)).isEqualTo(true);
        assertThat(cache.add(base)).isEqualTo(false);
        assertThat(cache.update(base)).isEqualTo(true);
        cache.delete(base);
        assertThat(cache.update(base)).isEqualTo(false);
    }

    @Test
    public void when1AddAndUpdateWithDifferentVersion() {
        Cache cache = new Cache();
        Base base1 = new Base(1, 1);
        Base base2 = new Base(1, 2);
        cache.add(base1);
        assertThatThrownBy(() -> cache.update(base2));
    }
}