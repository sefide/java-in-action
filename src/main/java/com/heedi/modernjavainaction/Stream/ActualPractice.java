package com.heedi.modernjavainaction.Stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ActualPractice {

    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        // 2021년 트랜잭션, 값 기준 오름차
        List<Transaction> ordered2021Transactions = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparingInt(Transaction::getValue))
                .collect(Collectors.toList());

        // 거래자가 근무하는 모든 도시 중복없이 나열
        transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getCity)
                .distinct() // toSet으로 반환 시, 자동 중복 제
                .forEach(System.out::println);

        // 케임브리지 근무하는 모든 거래자 이름순 정렬
        List<Transaction> cambridgeTraders = transactions.stream()
                .filter(transaction -> "Cambridge".equals(transaction.getTrader().getCity()))
                .distinct()
                .sorted(Comparator.comparing(t -> t.getTrader().getName()))
                .collect(Collectors.toList());

        // 모든 거래자 이름순 정렬
        String names = transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getName)
                .distinct()
                .sorted()
                .reduce("", (n1, n2) -> n1 + n2);
        System.out.println(names);

        // 밀라노에 거래자가 있는지
        boolean hasMilanTrader = transactions.stream()
                .anyMatch(t -> "Milan".equals(t.getTrader().getCity()));

        // 케임브리지에 거주하는 거래자의 모든 트랜잭션값을 출력
        transactions.stream()
                .filter(transaction -> "Cambridge".equals(transaction.getTrader().getCity()))
                .map(Transaction::getValue)
                .forEach(System.out::println);

        // 전체 트랜잭션 중 최댓값
        Optional<Integer> max = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);

        // 전체 트랜잭션 중 최솟값
        Optional<Integer> min = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::min);

        // 최댓값 Comparator 함수 사용한 방법
        Optional<Transaction> maxUsingComparator = transactions.stream()
                .max(Comparator.comparing(Transaction::getValue));

        System.out.println("max : " + max + ", min : " + min);
        System.out.println("optional max : " + maxUsingComparator);
    }
}
