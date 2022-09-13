package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тестовый класс для класса подсчета сумм.
 * Created by Oywayten on 12.09.2022.
 */
class RolColSumTest {

    @Test
    public void whenMatrixSize3AsyncIsSlowerThenInSeries() throws ExecutionException, InterruptedException {
        int[][] ints = RolColSum.matrixGen(3);
        long startAsync = System.nanoTime();
        RolColSum.asyncSum(ints);
        long stopAsync = System.nanoTime();
        long difAsync = stopAsync - startAsync;
        long startSeries = System.nanoTime();
        RolColSum.inSeriesSum(ints);
        long stopSeries = System.nanoTime();
        long difSeries = stopSeries - startSeries;
        assertThat(difAsync > difSeries).isEqualTo(true);
    }

    @Test
    public void whenMatrixSize100AsyncIsSlowerThenInSeries() throws ExecutionException, InterruptedException {
        int[][] ints = RolColSum.matrixGen(100);
        long startAsync = System.nanoTime();
        RolColSum.asyncSum(ints);
        long stopAsync = System.nanoTime();
        long difAsync = stopAsync - startAsync;
        long startSeries = System.nanoTime();
        RolColSum.inSeriesSum(ints);
        long stopSeries = System.nanoTime();
        long difSeries = stopSeries - startSeries;
        assertThat(difAsync > difSeries).isEqualTo(true);
    }

    @Test
    public void whenMatrixSize3AsyncIsArr636669() throws ExecutionException, InterruptedException {
        int[][] ints = RolColSum.matrixGen(3);
        RolColSum.Sums[] result = RolColSum.asyncSum(ints);
        RolColSum.Sums[] expected = new RolColSum.Sums[3];
        expected[0] = new RolColSum.Sums();
        expected[1] = new RolColSum.Sums();
        expected[2] = new RolColSum.Sums();
        expected[0].setRowSum(6);
        expected[0].setColSum(3);
        expected[1].setRowSum(6);
        expected[1].setColSum(6);
        expected[2].setRowSum(6);
        expected[2].setColSum(9);
        assertThat(result).containsExactly(expected);
    }

    @Test
    public void whenMatrixSize3InSeriesIsArr636669() {
        int[][] ints = RolColSum.matrixGen(3);
        RolColSum.Sums[] sums1 = RolColSum.inSeriesSum(ints);
        RolColSum.Sums[] expected = new RolColSum.Sums[3];
        expected[0] = new RolColSum.Sums();
        expected[1] = new RolColSum.Sums();
        expected[2] = new RolColSum.Sums();
        expected[0].setRowSum(6);
        expected[0].setColSum(3);
        expected[1].setRowSum(6);
        expected[1].setColSum(6);
        expected[2].setRowSum(6);
        expected[2].setColSum(9);
        assertThat(sums1).containsExactly(expected);
    }
}