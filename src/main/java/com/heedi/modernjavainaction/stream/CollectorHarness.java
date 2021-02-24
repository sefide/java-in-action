package com.heedi.modernjavainaction.stream;

import static com.heedi.modernjavainaction.stream.PartitioningPrime.partitionPrimes;
import static com.heedi.modernjavainaction.stream.PartitioningPrime.partitionPrimesWithCustomCollector;

public class CollectorHarness {
    public static void main(String[] args) {
        long fastest = Long.MAX_VALUE;

        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();

//            partitionPrimes(1_000_000); // 429653396 msecs
            partitionPrimesWithCustomCollector(1_000_000); // 286701068 msecs

            long duration = (System.nanoTime()) - start;
            if(duration < fastest) {
                fastest = duration;
            }
        }
        System.out.println("fastest execution done in " + fastest + " msecs");
    }
}
