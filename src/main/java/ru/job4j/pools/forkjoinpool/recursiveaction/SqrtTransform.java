package ru.job4j.pools.forkjoinpool.recursiveaction;

import java.util.concurrent.RecursiveAction;

/**
 * Применение {@link java.util.concurrent.ForkJoinPool} и {@link java.util.concurrent.RecursiveAction}
 * для преобразования элементов массива типа {@code double} в их квадратные корни.
 *
 * @author oywayten (cpc1251@mail.ru)
 * @version 1.0, 05.09.2022
 */
public class SqrtTransform extends RecursiveAction {
    /**
     * Пороговое значение
     */
    final int seqThreshold = 10000;
    /**
     * Обрабатываемый массив
     */
    double[] data;
    int start;
    int end;

    public SqrtTransform(double[] data, int start, int end) {
        this.data = data;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start < seqThreshold) {
            for (int i = start; i < end; i++) {
                data[i] = Math.sqrt(data[i]);
            }
        } else {
            int middle = (start + end) / 2;
            invokeAll(new SqrtTransform(data, start, middle));
            invokeAll(new SqrtTransform(data, middle, end));
        }
    }
}

class ForkJoinDemo {
    public static void main(String[] args) {
        double[] nums = new double[100000];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = i;
        }
        System.out.println("Часть исходной последовательности");
        for (int i = 0; i < 10; i++) {
            System.out.print(nums[i] + " ");
        }
        System.out.println();
        SqrtTransform task = new SqrtTransform(nums, 0, nums.length);
        /*ForkJoinPool fjp = ForkJoinPool.commonPool();
        fjp.invoke(task);*/
        task.invoke();
        System.out.println("Часть преобразований последовательности"
                + " с точностью до 4 знаков после точки: ");
        for (int i = 0; i < 10; i++) {
            System.out.format("%.4f ", nums[i]);
        }
        System.out.println();
    }
}
