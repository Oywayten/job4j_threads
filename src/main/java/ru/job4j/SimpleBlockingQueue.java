package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Это класс блокирующей очереди, ограниченной по размеру.
 * Если очередь заполнена полностью, то при попытке добавления поток Producer блокируется,
 * до тех пор пока Consumer не извлечет очередные данные, т.е. в очереди появится свободное место.
 * И наоборот если очередь пуста поток Consumer блокируется, до тех пор пока Producer не поместит в очередь данные.
 *
 * @param <T> параметризованный тип
 * @author oywayten (cpc1251@mail.ru)
 * @version 1
 * @since 16.08.2022
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    /**
     * Метод добавляет объект параметризованного типа в очередь
     *
     * @param value объект для добавления в очередь {@link #queue}
     */
    public void offer(T value) throws InterruptedException {
        synchronized (this) {
            while (queue.size() == limit) {
                this.wait();
            }
            queue.offer(value);
            this.notifyAll();
        }
    }

    /**
     * Метод возвращает объект из внутренней коллекции.
     * Если в коллекции объектов нет, то переводит текущую нить в состояние ожидания.
     *
     * @return объект из очереди {@link #queue}
     */
    public T poll() throws InterruptedException {
        synchronized (this) {
            while (queue.size() == 0) {
                this.wait();
            }
            T poll = queue.poll();
            this.notifyAll();
            return poll;
        }
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}