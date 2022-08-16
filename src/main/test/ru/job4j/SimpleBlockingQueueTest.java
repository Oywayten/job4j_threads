package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {

    @Test
    public void whenVolume3AndIntegers5() {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        Thread producer = new Thread(() -> {
            queue.offer(1);
            queue.offer(2);
            queue.offer(3);
            queue.offer(4);
            queue.offer(5);
        });
        Thread consumer = new Thread(() -> {
            assertThat(queue.poll()).isEqualTo(1);
            assertThat(queue.poll()).isEqualTo(2);
            assertThat(producer.getState()).isEqualTo(Thread.State.WAITING);
            assertThat(queue.poll()).isEqualTo(3);
            assertThat(queue.poll()).isEqualTo(4);
            assertThat(queue.poll()).isEqualTo(5);
        });
        producer.start();
        consumer.start();
    }
}