package com.heedi.modernjavainaction.stream;

import java.util.concurrent.RecursiveTask;

public class ForkJoinSumCalculator extends RecursiveTask<Long> {
    private final long[] numbers;
    private final int start;
    private final int end;
    public static final long THRESHOLD = 10_000;

    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    public ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end - start;
        if (length <= THRESHOLD) {
            return computeSequentially();
        }
        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length / 2);
        leftTask.fork(); // ForkJoinPool의 다른 스레드로 새로운 태스크 실행

        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length / 2, end);
        Long rightResult = rightTask.compute(); // 두 번째 서브태스크 동기 실행
        Long leftResult = leftTask.join(); // 첫 번째 서브태스크읙 결과를 기다리거나 완료되면 읽는다.

        return leftResult + rightResult;
    }

    private long computeSequentially() {
        long sum = 0;
        for (int i = 0; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }
}
