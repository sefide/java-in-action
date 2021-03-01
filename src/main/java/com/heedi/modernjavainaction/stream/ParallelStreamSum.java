package com.heedi.modernjavainaction.stream;

import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ParallelStreamSum {

    public static void main(String[] args) {

        long sum = parallelSum(5L);
        System.out.println(sum);

        // 스레드 풀 개수
        int processorsNumber = Runtime.getRuntime().availableProcessors();
        System.out.println(processorsNumber); // 4
//        System.setProperty("java.util.concurrett.ForkJoinPool.common.parallelism", "12");
    }

    public static long iterativeSum(long n) {
        long result = 0;
        for (long i = 1L; i <= n; i++) {
            result += i;
        }

        return result;
    }

    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .reduce(0L, Long::sum);
    }

    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .parallel() // 병렬 스트림으로 변환
                .reduce(0L, Long::sum);
    }

    public static long rangedSum(long n) {
        return LongStream.range(1L, n)
                .reduce(Long::sum)
                .getAsLong();
    }

    public static long parallelRangedSum(long n) {
        return LongStream.range(1L, n)
                .parallel()
                .reduce(Long::sum)
                .getAsLong();
    }

    public static long sideEffectSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.range(1L, n).forEach(accumulator::add);
        return accumulator.total;
    }

    public static long sideEffectParallelSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.range(1L, n).parallel().forEach(accumulator::add);
        return accumulator.total;
    }

    public static class Accumulator {
        long total = 0L;

        public void add(long value) {
            total += value;
        }
    }
}
