package com.heedi.modernjavainaction.Stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamMapping {

    public static void main(String[] args) {
        // Quiz5-2 - 1
        List<Integer> target = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> squaredTarget = target.stream()
                .map(a -> a * a)
                .collect(Collectors.toList());

        // Quiz5-2 - 2
        List<Integer> pair1 = Arrays.asList(1, 2, 3);
        List<Integer> pair2 = Arrays.asList(3, 4);

        List<int[]> pair = pair1.stream()
                .flatMap(a -> pair2.stream()
                        .map(b -> new int[]{a, b}))
                .collect(Collectors.toList());

        // Quiz5-2 - 3
        List<int[]> threePair = pair1.stream()
                .flatMap(a -> pair2.stream()
                        .filter(b -> (a + b) % 3 == 0)
                        .map(b -> new int[]{a, b}))
                .collect(Collectors.toList());
    }
}
