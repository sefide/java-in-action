package com.heedi.modernjavainaction.stream;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Pythagoras {

    public static void main(String[] args) {

        Stream<int[]> numbers = IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100) // Stream<Integer>
                        .filter(b -> Math.sqrt((a * a) + (b * b)) % 1 == 0)
                        .mapToObj(b -> new int[]{a, b, (int) Math.sqrt((a * a) + (b * b))}) // Stream<int[]>
                );

        numbers
                .limit(5)
                .forEach(numberSet -> System.out.println(Arrays.toString(numberSet)));
    }
}
