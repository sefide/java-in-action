package com.heedi.modernjavainaction.stream;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

public class ForkJoinSum {

    public static void main(String[] args) {
        long n = 5L;
        long result = forkJoinSum(n);

        System.out.println(result);
    }

    private static long forkJoinSum(long n) {
        long[] numbers = LongStream.rangeClosed(1, n)
                .toArray();

        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);

        // JVM에서 이용할 수 있는 모든 프로세서가 자유롭게 풀에 접근 가능
        return new ForkJoinPool().invoke(task); // task의 결과값 반환
    }
}
