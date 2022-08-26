package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс пула потоков для выполнения задач
 *
 * @author oywayten (cpc1251@mail.ru)
 * @version 1
 * @since 26.08.2022
 */
public class ThreadPool {
    /**
     * Количество ядер в системе для инициализации пула
     */
    public static final int SIZE = Runtime.getRuntime().availableProcessors();
    /**
     * Список потоков для реализации пула
     */
    private final List<Thread> threads = new LinkedList<>();
    /**
     * Блокирующая очередь. Если в очереди нет элементов, то нить переводится в состояние Thread.State.WAITING.
     * Когда приходит новая задача, всем нитям в состоянии Thread.State.WAITING посылается сигнал проснуться и
     * начать работу.
     */
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(SIZE);

    public ThreadPool() {
        getThreads();
    }

    /**
     * Инициализация пула по количеству ядер в системе {@link #SIZE}
     */
    private void getThreads() {
        for (int i = 0; i < SIZE; i++) {
            new Thread(() -> {
                try {
                    tasks.poll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    /**
     * Метод добавляет задачи в блокирующую очередь tasks
     *
     * @param job задача
     */
    public void work(Runnable job) {
        try {
            tasks.offer(job);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }
}