package ru.job4j.pools;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Класс считает суммы по строкам и столбцам квадратной матрицы.
 * Created by Oywayten on 12.09.2022.
 */
public class RolColSum {

    /**
     * Вспомогательный метод для генерации квадратной матрицы int[][] для тестов.
     *
     * @param size размер матрицы для генерации
     * @return квадратную матрицу типа int[][]
     */
    public static int[][] matrixGen(int size) {
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = j + 1;
            }
        }
        return matrix;
    }

    /**
     * Метод выполняет последовательный подсчет суммы элементов строк и столбцов квадратной матрицы.
     *
     * @param matrix матрица для подсчета сумм столбцов и строк
     * @return массив сумм по i-той строке и столбцу типа Sum[]
     */
    public static Sums[] sum(int[][] matrix) {
        Sums[] arr = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            Sums tmp = new Sums();
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            tmp.setRowSum(rowSum);
            tmp.setColSum(colSum);
            arr[i] = tmp;
        }
        return arr;
    }

    /**
     * Метод выполняет параллельный подсчет суммы элементов строк и столбцов квадратной матрицы.
     *
     * @param matrix матрица для подсчета сумм столбцов и строк
     * @return массив сумм по i-той строке и столбцу типа Sum[]
     */
    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] arr = new Sums[matrix.length];
        Map<Integer, CompletableFuture<Sums>> futureMap = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            futureMap.put(i, getTask(matrix, i));
        }
        for (Integer key : futureMap.keySet()) {
            arr[key] = futureMap.get(key).get();
            System.out.println(arr[key]);
        }
        return arr;
    }

    /**
     * Вспомогательный метод для получения Sum со значениями сумм по заданной строке и столбцу.
     *
     * @param matrix матрица, по которой выполняют вычисления
     * @param num    заданный номер строки и столбца
     * @return возвращает CompletableFuture<Sums> для последующей обработки в основном методе
     */
    private static CompletableFuture<Sums> getTask(int[][] matrix, int num) {
        return CompletableFuture.supplyAsync(() -> {
            Sums tmp = new Sums();
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < matrix[num].length; j++) {
                rowSum += matrix[num][j];
                colSum += matrix[j][num];
            }
            tmp.setRowSum(rowSum);
            tmp.setColSum(colSum);
            return tmp;
        });
    }

    /**
     * Модель данных суммы строк и столбцов.
     */
    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Sums{");
            sb.append("rowSum=").append(rowSum);
            sb.append(", colSum=").append(colSum);
            sb.append('}');
            return sb.toString();
        }
    }

}