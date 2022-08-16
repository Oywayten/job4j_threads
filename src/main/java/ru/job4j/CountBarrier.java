package ru.job4j;

/**
 * Класс блокирует выполнение по условию счетчика.
 *
 * @author oywayten (cpc1251@mail.ru)
 * @version 1
 * @since 16.08.2022
 */
public class CountBarrier {
    private final Object monitor = this;
    /**
     * содержит ожидаемое количество вызовов метода count()
     */
    private final int total;
    /**
     * содержит фактическое количество вызовов метода count()
     */
    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    /**
     * Метод считает сколько раз его вызвали и записывает это в поле count
     * После каждого изменения программа будит нити, которые ждут изменений.
     */
    public void count() {
        synchronized (monitor) {
            count++;
            monitor.notifyAll();
        }
    }

    /**
     * Метод пропускает дальше работу нити, если count >= total
     */
    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}