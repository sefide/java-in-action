package com.heedi.modernjavainaction.functional;

import java.util.stream.LongStream;

public class FactorialMain {

    public static void main(String[] args) {
        int n = 5;
        System.out.println(factorialWithFor(n));
        System.out.println(factorialWithRecursive(n));
        System.out.println(factorialWithStream(n));
        System.out.println(factorialWithTailRecursive(n));

    }

    private static long factorialWithFor(int n) {
        int r = 1;

        for (int i = 1; i <= n; i++) {
            r *= i;
        }
        return r;
    }

    private static long factorialWithRecursive(int n) {
        // 호출될 때마다 호출 스택에 각 호출 시 생성되는 정보를 저장할 새롱운 스택 프레임 생성
        // StackOverflowError 발생 위험
        return n == 1 ? 1 : n * factorialWithRecursive(n - 1);
    }

    private static long factorialWithStream(int n) {
        return LongStream.rangeClosed(1, n)
                .reduce(1, (long a, long b) -> a * b);
    }

    private static long factorialWithTailRecursive(int n) {
        // 꼬리 호출 최적화
        return factorialHelper(1, n);
    }

    private static long factorialHelper(int acc, int n) {
        // 중간 결과 (acc * n)를 함수의 인수로 직접 전달하면서 (일반 재귀와의 차이)
        // 컴파일러가 하나의 스택 프레임을 제활용할 가능성이 생김
        // 자바에서는 이러한 최적화가 적용되지 않지만 습관화하는게 좋다.
        return n == 1 ? acc : factorialHelper(acc * n, n - 1);
    }

}
