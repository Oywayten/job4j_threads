package ru.job4j.callable;

import java.util.concurrent.*;

/**
 * Применение {@link Callable} для параллельного вычисления разных задач и
 * {@link Future} для получения возвращаемого значения.
 *
 * @author oywayten (cpc1251@mail.ru)
 * @version 1.0, 04.09.2022
 */
public class CallableDemo {
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(3);
        Future<Integer> f1;
        Future<Double> f2;
        Future<Integer> f3;
        System.out.println("Запуск");
        f1 = es.submit(new Sum(10));
        f2 = es.submit(new Hypot(3, 4));
        f3 = es.submit(new Factorial(5));
        try {
            System.out.println(f1.get());
            System.out.println(f2.get());
            System.out.println(f3.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        es.shutdown();
        System.out.println("Завершение");
    }
}

/**
 * Класс вычисляет сумму.
 */
class Sum implements Callable<Integer> {
    int stop;

    public Sum(int stop) {
        this.stop = stop;
    }

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 1; i <= stop; i++) {
            sum += 1;
        }
        return sum;
    }
}

/**
 * Класс вычисляет гипотенузу прямоугольника по его известным сторонам.
 */
class Hypot implements Callable<Double> {
    double side1;
    double side2;

    public Hypot(double side1, double side2) {
        this.side1 = side1;
        this.side2 = side2;
    }

    @Override
    public Double call() throws Exception {
        return Math.sqrt((side1 * side1) + (side2 * side2));
    }
}

/**
 * Класс вычисляет факториал заданного числа.
 */
class Factorial implements Callable<Integer> {
    int stop;

    public Factorial(int stop) {
        this.stop = stop;
    }

    @Override
    public Integer call() throws Exception {
        int fact = 1;
        for (int i = 2; i <= stop; i++) {
            fact *= i;
        }
        return fact;
    }
}