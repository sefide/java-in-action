package com.heedi.modernjavainaction.collection;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapMethod {

    public static void main(String[] args) {
        ConcurrentHashMap<String, Long> map1 = new ConcurrentHashMap<>();
        long parallelismThreshold = 1;
        Optional<Long> maxValue = Optional.ofNullable(map1
                .reduceValues(parallelismThreshold, Long::max));


        ConcurrentHashMap<String, List<Long>> map2 = new ConcurrentHashMap<>();
        map2.put("1", Arrays.asList(1L, 2L, 3L));
        map2.put("2", Arrays.asList(4L, 5L, 6L, 7L));

        System.out.println(map2.size());
        System.out.println(map2.mappingCount());

        map1.put("1", 1L);
        ConcurrentHashMap.KeySetView<String, Long> keySetView = map1.keySet();
        System.out.println("keySetView : " + keySetView);

        map1.put("2", 4L);
        System.out.println("keySetView : " + keySetView);

        ConcurrentHashMap.KeySetView<String, Boolean> newKeySetView = ConcurrentHashMap.newKeySet();
    }
}
