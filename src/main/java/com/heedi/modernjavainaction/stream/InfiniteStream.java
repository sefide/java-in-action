package com.heedi.modernjavainaction.stream;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class InfiniteStream {

    public static void main(String[] args) {
        // java9 iterator Predicate
        IntStream.iterate(0, i -> i < 5, i -> i += 1)
                .forEach(System.out::println);

        IntStream.iterate(0, i -> i += 1)
                .takeWhile(i -> i < 5)
                .forEach(System.out::println);

        /* infinite Stream
        IntStream.iterate(0, i -> i += 1)
                .filter(i -> i < 5)
                .forEach(System.out::println);
         */

        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);

         /* infinite Stream
        IntStream ones = IntStream.generate(() -> 1);
         */
    }

}
