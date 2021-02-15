package com.heedi.modernjavainaction.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Closure {
    private Integer b = 2;

    private Stream<Integer> calculate(Stream<Integer> stream, Integer a) {
        // Variable used in lambda expression should be final or effective final
        // a = 1;
        return stream.map(t -> t * a + b);
    }

    public static void main(String... args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

        List<Integer> result = new Closure()
                .calculate(list.stream(), 3)
                .collect(Collectors.toList());

        System.out.println(result); // [5, 8, 11, 14, 17]
    }

}
