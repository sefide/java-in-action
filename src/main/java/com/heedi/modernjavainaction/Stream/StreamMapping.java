package com.heedi.modernjavainaction.Stream;

import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamMapping {

    public static void main(String[] args) {
        // Quiz6 - 1
        List<Integer> target = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> squaredTarget = target.stream()
                .map(a -> a * a)
                .collect(Collectors.toList());

        // Quiz6 - 2
        List<Integer> pair1 = Arrays.asList(1, 2, 3);
        List<Integer> pair2 = Arrays.asList(3, 4);

        List<List<Integer>> pair = pair1.stream()
                .map(a -> pair2.stream().map(b -> Arrays.asList(a, b)).collect(Collectors.toList()))
                .flatMap(List::stream)
                .collect(Collectors.toList());

        // Quiz6 - 3
        List<List<Integer>> threePair = pair1.stream()
                .map(a -> pair2.stream().map(b -> Arrays.asList(a, b)).collect(Collectors.toList()))
                .flatMap(List::stream)
                .filter(a -> a.stream().reduce(Integer::sum).get() % 3 == 0)
                .collect(Collectors.toList());

        System.out.println();
    }
}
