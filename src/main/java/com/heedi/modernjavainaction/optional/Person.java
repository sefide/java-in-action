package com.heedi.modernjavainaction.optional;

import java.util.Optional;

public class Person {
    private final Optional<Car> car;

    public Person(Car car) {
        this.car = Optional.ofNullable(car);
    }

    public Optional<Car> getCar() {
        return car;
    }

}
