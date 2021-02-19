package com.heedi.modernjavainaction.stream;

import java.util.stream.Stream;

public class Fibonacci {

    public static void main(String[] args) {

        // 피보나치 집합 20개 생성
        Stream.iterate(new int[]{0, 1}, n -> new int[]{n[1], (n[0] + n[1])})
                .limit(10)
                .forEach(t -> System.out.println("(" + t[0] + "," + t[1] + ")"));

        // 피보나치 수열값 생성
        Stream.iterate(new int[]{0, 1}, n -> new int[]{n[1], (n[0] + n[1])})
                .limit(10)
                .map(t -> t[0])
                .forEach(System.out::println);

    }
}
