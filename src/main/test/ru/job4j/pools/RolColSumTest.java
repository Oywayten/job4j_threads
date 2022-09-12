package ru.job4j.pools;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тестовый класс для класса подсчета сумм.
 * Created by Oywayten on 12.09.2022.
 */
class RolColSumTest {

    @Disabled
    @Test
    public void whenMatrixSize3AsyncIsFasterThenInSeries() throws ExecutionException, InterruptedException {
        int[][] ints = RolColSum.matrixGen(3);
        long startAsync = System.nanoTime();
        RolColSum.asyncSum(ints);
        long stopAsync = System.nanoTime();
        long difAsync = stopAsync - startAsync;
        System.out.println(difAsync);
        long startSeries = System.nanoTime();
        RolColSum.sum(ints);
        long stopSeries = System.nanoTime();
        long difSeries = stopSeries - startSeries;
        System.out.println(difSeries);
        assertThat(difAsync < difSeries).isEqualTo(true);
    }

    @Disabled
    @Test
    public void whenMatrixSize100AsyncIsFasterThenInSeries() throws ExecutionException, InterruptedException {
        int[][] ints = RolColSum.matrixGen(100);
        long startAsync = System.nanoTime();
        RolColSum.asyncSum(ints);
        long stopAsync = System.nanoTime();
        long difAsync = stopAsync - startAsync;
        System.out.println(difAsync);
        long startSeries = System.nanoTime();
        RolColSum.sum(ints);
        long stopSeries = System.nanoTime();
        long difSeries = stopSeries - startSeries;
        System.out.println(difSeries);
        assertThat(difAsync < difSeries).isEqualTo(true);
    }
}