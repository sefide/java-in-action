package com.heedi.modernjavainaction.stream;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Pythagoras {

    public static void main(String[] args) {

        Stream<double[]> numbers = IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100) // Stream<Integer>
                        .mapToObj(b -> new double[]{a, b, Math.sqrt((a * a) + (b * b))})
                        .filter(t -> t[2] % 1 == 0)
                );

        numbers
                .limit(5)
                .forEach(numberSet -> System.out.println(Arrays.toString(numberSet)));
    }
}
