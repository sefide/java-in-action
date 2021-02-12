package com.heedi.modernjavainaction.behaviorParameterization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilteringApples {
    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(
                new Apple(80, Color.GREEN),
                new Apple(155, Color.GREEN),
                new Apple(120, Color.RED)
        );

//        List<Apple> greenApples = filterApples(inventory, Color.GREEN, 0, true);
//        List<Apple> heavyApples = filterApples(inventory, null, 150, false);

        List<Apple> greenApples = filterApples(inventory, new AppleGreenColorPredicate());
        List<Apple> heavyApples = filterApples(inventory, new AppleHeavyWeightPredicate());

        prettyPrintApple(greenApples, new AppleColorPrintFunction());
        prettyPrintApple(heavyApples, new AppleWeightPrintFunction());
    }

    public static void prettyPrintApple(List<Apple> inventory, PrintFunction printFunction) {
        for (Apple apple : inventory) {
            String output = printFunction.getOutput(apple);
            System.out.println(output);
        }
    }

    public interface PrintFunction {
        String getOutput(Apple apple);
    }

    public static class AppleWeightPrintFunction implements PrintFunction {

        @Override
        public String getOutput(Apple apple) {
            return String.valueOf(apple.getWeight());
        }
    }

    public static class AppleColorPrintFunction implements PrintFunction {

        @Override
        public String getOutput(Apple apple) {
            return apple.getColor().name();
        }
    }


    /* 모든 속성으로 필터링 : 아래 방식 비추천 */
    public static List<Apple> filterApples(List<Apple> inventory, Color color, int weight, boolean flag) {
        List<Apple> result = new ArrayList<>();

        for (Apple apple : inventory) {
            if ((flag && apple.getColor().equals(color)) || (!flag && apple.getWeight() > weight)) {
                result.add(apple);
            }
        }

        return result;
    }

    /* 추상적 조건으로 필터링 : 전략 디자인 패턴 */
    public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate applePredicate) {
        List<Apple> result = new ArrayList<>();

        for (Apple apple : inventory) {
            if (applePredicate.test(apple)) {
                result.add(apple);
            }
        }

        return result;
    }

    public interface ApplePredicate {
        boolean test(Apple apple);
    }

    public static class AppleHeavyWeightPredicate implements ApplePredicate {
        @Override
        public boolean test(Apple apple) {
            return apple.getWeight() > 150;
        }
    }

    public static class AppleGreenColorPredicate implements ApplePredicate {
        @Override
        public boolean test(Apple apple) {
            return apple.getColor().equals(Color.GREEN);
        }
    }

    public enum Color {
        GREEN, RED
    }

    public static class Apple {
        private int weight;
        private Color color;

        public Apple(int weight, Color color) {
            this.weight = weight;
            this.color = color;
        }

        public int getWeight() {
            return weight;
        }

        public Color getColor() {
            return color;
        }
    }
}
