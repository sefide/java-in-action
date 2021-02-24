package com.heedi.modernjavainaction.stream;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.partitioningBy;

public class PartitioningPrime {

    public static void main(String[] args) {
        int n = 11;
        Map<Boolean, List<Integer>> partitionPrimes = partitionPrimes(n);
        System.out.println(partitionPrimes);

        Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector = partitionPrimesWithCustomCollector(n);
        System.out.println(partitionPrimesWithCustomCollector);
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

    public static Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector(int n) {
        return IntStream.rangeClosed(2, n)
                .boxed()
                .collect(new PrimeNumbersCollector());
    }

    public static boolean isPrime(List<Integer> primes, int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);

        return primes.stream()
                .takeWhile(i -> i <= candidateRoot)
                .noneMatch(i -> candidate % i == 0);
    }

    public static boolean isPrimeJava8(List<Integer> primes, int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);

        return takeWhileJava8(primes, i -> i <= candidateRoot)
                .stream()
                .noneMatch(i -> candidate % i == 0);
    }

    public static <A> List<A> takeWhileJava8(List<A> list, Predicate<A> p) {
        int i = 0;

        for (A item : list) {
            if (!p.test(item)) {
                return list.subList(0, i);
            }
            i++;
        }
        return list;
    }
}
