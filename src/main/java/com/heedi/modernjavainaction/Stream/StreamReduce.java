package com.heedi.modernjavainaction.Stream;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StreamReduce {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        // 최댓값 구하기
        Optional<Integer> maxNumber = numbers.stream()
                .reduce(Integer::max);

        //Quiz5-3
        // 맵 리듀스 패턴
        int count = numbers.stream()
                .map(i -> 1)
                .reduce(0, Integer::sum);
        System.out.println(count);

        long sameCount = numbers.stream()
                .count();
        System.out.println(count);
    }
}
