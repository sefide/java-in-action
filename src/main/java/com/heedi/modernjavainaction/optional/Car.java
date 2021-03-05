package com.heedi.modernjavainaction.optional;

import java.util.Optional;

public class Car {
    private final Optional<Insurance> insurance;

    public Car(Insurance insurance) {
        this.insurance = Optional.ofNullable(insurance);
    }

    public Optional<Insurance> getInsurance() {
        return insurance;
    }
}
