package com.heedi.modernjavainaction.stream;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static com.heedi.modernjavainaction.stream.PartitioningPrime.isPrime;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

public class PrimeNumbersCollector implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {
    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
        return HashMap::new;
    }

    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
        return ((Map<Boolean, List<Integer>> map, Integer candidate) -> {
            map.get(isPrime(map.get(TRUE), candidate))
                    .add(candidate);
        });
    }

    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
        // 병렬 사용이 불가능하다면, UnsupportedOperationException을 던져도 좋다.
        return (map1, map2) -> {
            map1.get(TRUE).addAll(map2.get(TRUE));
            map1.get(FALSE).addAll(map2.get(FALSE));
            return map1;
        };
    }

    @Override
    public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH));
    }
}
