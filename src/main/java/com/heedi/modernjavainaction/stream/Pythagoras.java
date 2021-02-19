package com.heedi.modernjavainaction.stream;

import java.util.stream.IntStream;

public class Pythagoras {

    public static void main(String[] args) {
        int[] numbers = new int[]{3, 4, 5};
        int a = 3;

        double c = IntStream.range(1, 100)
                .filter(i -> Math.sqrt((a * a) + (i * i)) % 1 == 0)
                .peek(b -> System.out.println("b is " + b))
                .boxed()
                .map(b -> Math.sqrt((a * a) + (b * b)))
                .findFirst()
                .orElse(0d);

        System.out.println(c);
    }
}
