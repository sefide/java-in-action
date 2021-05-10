package com.heedi.modernjavainaction.functional;

import java.util.function.DoubleUnaryOperator;

public class CurryingMain {

    public static void main(String[] args) {
        double x = 3;
        double f = 9.0 / 5;
        double b = 32;

        double result1 = converter(x, f, b);
        System.out.println(result1);

        DoubleUnaryOperator convertCtoF = curriedConverter(9.0/5, 32);
        DoubleUnaryOperator convertUSDtoGBP = curriedConverter(0.6, 0);
        DoubleUnaryOperator convertKmtoMi = curriedConverter(0.6214, 0);

        double result2 = convertUSDtoGBP.applyAsDouble(7);
        System.out.println(result2);
        System.out.println(result2 == 4.2d);
    }

    static double converter(double x, double f, double b) {
        return x * f + b;
    }

    static DoubleUnaryOperator curriedConverter(double f, double b) {
        return (double x) -> x * f + b;
    }
}
