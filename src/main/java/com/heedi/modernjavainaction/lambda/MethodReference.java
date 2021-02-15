package com.heedi.modernjavainaction.lambda;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class MethodReference {
    public static void main(String[] args) {
        Supplier<Cat> lambdaSupplier = () -> new Cat();
        Cat lambdaPeople = lambdaSupplier.get();

        Supplier<Cat> referenceSupplier = Cat::new;
        Cat referencePeople = referenceSupplier.get();

        BiFunction<String, String, Cat> lambdaFunction = (name, character) -> new Cat(name, character);
        Cat lambdaPeople2 = lambdaFunction.apply("momo", "magnanimous");

        BiFunction<String, String, Cat> referenceFunction = Cat::new;
        Cat referencePeople2 = referenceFunction.apply("dd", "smart");

        TriFunction<String, String, Integer, Cat> referenceTriFunction = Cat::new;
        Cat referencePeople3 = referenceTriFunction.apply("dd", "smart", 1);
    }

    static class Cat {
        private String name;
        private String character;
        private int age;

        public Cat() {

        }

        public Cat(String name, String character) {
            this.name = name;
            this.character = character;
        }

        public Cat(String name, String character, int age) {
            this.name = name;
            this.character = character;
            this.age = age;
        }
    }
}
