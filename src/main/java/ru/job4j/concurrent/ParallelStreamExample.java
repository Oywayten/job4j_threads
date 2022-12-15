package ru.job4j.concurrent;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by Oywayten on 15.12.2022.
 */
public class ParallelStreamExample {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> multiplication = list.stream()
                .reduce((a, b) -> a * b);
        System.out.println(multiplication.get());
        Stream<Integer> stream = list.parallelStream();
        System.out.println(stream.isParallel());
        Optional<Integer> reduce = stream.reduce((a, b) -> a * b);
        System.out.println(reduce.get());
        Stream<Integer> stream1 = list.parallelStream();
        List<Integer> integers = stream1.sorted().peek(System.out::println).toList();
    }
}