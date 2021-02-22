package com.heedi.modernjavainaction.stream;

public class Dish {
    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Type type;

    public Dish(String name, boolean vegetarian, int calories, Type type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isMeat() {
        return type.equals(Type.MEAT);
    }

    public enum Type {
        FISH,
        MEAT,
        OTHER,
    }
}
