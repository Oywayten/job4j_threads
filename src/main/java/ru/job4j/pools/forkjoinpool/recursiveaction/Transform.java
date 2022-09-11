package ru.job4j.pools.forkjoinpool.recursiveaction;

import java.util.concurrent.RecursiveAction;

/**
 * Измерение эффекта от изменения порогового значения и
 * уровня параллелизма.
 *
 * @author oywayten (cpc1251@mail.ru)
 * @version 1.0, 05.09.2022
 */
class Transform extends RecursiveAction {
    double[] data;
    int start;
    int end;
    int seqTreshold;

    public Transform(double[] data, int start, int end, int seqTreshold) {
        this.data = data;
        this.start = start;
        this.end = end;
        this.seqTreshold = seqTreshold;
    }

    @Override
    protected void compute() {
        if (end - start < seqTreshold) {
            for (int i = start; i < end; i++) {
                if ((data[i] % 2) == 0) {
                    data[i] = Math.sqrt(data[i]);
                } else {
                    data[i] = Math.cbrt(data[i]);
                }
            }
        } else {
            int middle = (start + end) / 2;
            invokeAll(new Transform(data, start, middle, seqTreshold),
                    new Transform(data, middle, end, seqTreshold));
        }
    }
}
