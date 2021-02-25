package com.heedi.modernjavainaction.stream;

import java.util.stream.Stream;

public class ParallelStreamSum {

    public static void main(String[] args) {

        long sum = parallelSum(5L);
        System.out.println(sum);

        int processorsNumber = Runtime.getRuntime().availableProcessors();
        System.out.println(processorsNumber); // 4
//        System.setProperty("java.util.concurrett.ForkJoinPool.common.parallelism", "12");
    }

    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .parallel() // 병렬 스트림으로 변환
                .reduce(0L, Long::sum);
    }
}
