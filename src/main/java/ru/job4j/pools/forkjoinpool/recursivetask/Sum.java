package ru.job4j.pools.forkjoinpool.recursivetask;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Демонстрация применения RecursiveTask.
 * Класс вычисляет сумму элементов в массиве double[].
 *
 * @author oywayten (cpc1251@mail.ru)
 * @version 1.0, 08.09.2022
 */
public class Sum extends RecursiveTask<Double> {
    /**
     * Пороговое значение последовательного выполнения
     */
    final int threshold = 500;
    /**
     * Обрабатываемый массив
     */
    double[] data;
    int start;
    int end;

    public Sum(double[] data, int start, int end) {
        this.data = data;
        this.start = start;
        this.end = end;
    }

    public static void main(String[] args) {
        ForkJoinPool fjp = ForkJoinPool.commonPool();
        double[] nums = new double[5000];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = i % 2 == 0 ? i : -i;
        }
        boolean b = inForkJoinPool();
        System.out.println(b);
        Sum task = new Sum(nums, 0, nums.length);
        Double summation = task.invoke();
        task.reinitialize();
        System.out.println("Суммирование " + summation);
    }

    /**
     * The sum computation performed by array of double type.
     *
     * @return the result of the computation
     */
    @Override
    protected Double compute() {
        double sum = 0;
        if (end - start < threshold) {
            for (int i = start; i < end; i++) {
                sum += data[i];
                boolean b = inForkJoinPool();
                System.out.print(b + "\\\r + |\r + /\r");
            }
        } else {
            int middle = (end + start) / 2;
            Sum task1 = new Sum(data, start, middle);
            Sum task2 = new Sum(data, middle, end);
            task1.fork();
            task2.fork();
            sum = task1.join() + task2.join();
        }
        return sum;
    }
}
