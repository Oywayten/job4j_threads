package ru.job4j.pools.forkjoinpool.recursiveaction;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Класс демонстрирует влияние уровня параллелизма и
 * порогового значения при выполнении задач в классе ForkJoinTask.
 *
 * @author oywayten (cpc1251@mail.ru)
 * @version 1.0, 05.09.2022
 */
public class TransformWithConcurLevel extends RecursiveAction {
    double[] data;
    int start;
    int end;
    int seqTreshold;

    public TransformWithConcurLevel(double[] data, int start, int end, int seqTrashold) {
        this.data = data;
        this.start = start;
        this.end = end;
        this.seqTreshold = seqTrashold;
    }

    @Override
    protected void compute() {
        if (end - start < seqTreshold) {
            for (int i = start; i < end; i++) {
                if (data[i] % 2 == 0) {
                    data[i] = Math.sqrt(data[i]);
                } else {
                    data[i] = Math.cbrt(data[i]);
                }
            }
        } else {
            int middle = (start + end) / 2;
            invokeAll(new TransformWithConcurLevel(data, start, middle, seqTreshold),
                    new TransformWithConcurLevel(data, middle, end, seqTreshold));
        }
    }
}

class FJExperiment {
    public static void main(String[] args) {
        int pLevel;
        int threshold;
        if (args.length != 2) {
            System.out.println("использование: FJExperiment параллелизм порог ");
            return;
        }
        pLevel = Integer.parseInt(args[0]);
        threshold = Integer.parseInt(args[1]);
        long begin;
        long end;
        ForkJoinPool fjp = new ForkJoinPool(pLevel);
        double[] nums = new double[1_000_000];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = i;
        }
        TransformWithConcurLevel task = new TransformWithConcurLevel(nums, 0, nums.length, threshold);
        begin = System.nanoTime();
        fjp.invoke(task);
        end = System.nanoTime();
        System.out.println("Уровень параллелизма: " + pLevel);
        System.out.println("Порог последовательной обработки: " + threshold);
        System.out.println("истекшее время: " + (end - begin) / 1000000.0 + " с");
        System.out.println();
    }
}