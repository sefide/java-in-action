package com.heedi.modernjavainaction.stream;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.partitioningBy;

public class PartitioningPrime {

    public static void main(String[] args) {
        int n = 11;
        Map<Boolean, List<Integer>> partitionPrimes = partitionPrimes(n);

        System.out.println(partitionPrimes);
    }

    private static Map<Boolean, List<Integer>> partitionPrimes(int n) {
        return IntStream.rangeClosed(2, n)
                .boxed()
                .collect(partitioningBy(PartitioningPrime::isPrime));
    }

    public static boolean isPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);

        return IntStream.rangeClosed(2, candidateRoot)
                .noneMatch(i -> candidate % i == 0);
    }
}
