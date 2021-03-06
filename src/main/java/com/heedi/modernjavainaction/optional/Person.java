package com.heedi.modernjavainaction.optional;

import java.util.Optional;

public class Person {
    private final Optional<Car> car;
    private final int age;

    public Person(Car car, int age) {
        this.car = Optional.ofNullable(car);
        this.age = age;
    }

    public Optional<Car> getCar() {
        return car;
    }

    public int getAge() {
        return age;
    }
}
