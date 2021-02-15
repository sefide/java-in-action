package com.heedi.modernjavainaction.lambda;

import java.util.concurrent.Callable;
import java.util.function.Predicate;

public class Lambda {

    public static void main(String[] args) {
        Runnable runnable = () -> {};
        Callable<String> callable = () -> "Tricky example ;-)";
        Predicate<String> predicate = (String s) -> true;

        quiz3();
        freeVariable();
    }

    public static void quiz3() {
        // Target type of a lambda conversion must be an interface;
        // Object o1 = () -> {System.out.println("Tricky example");};

        Object o2 = (Runnable) () -> {
            System.out.println("Tricky example");
        };

        // Ambiguous method call : Both execute(Runnable) and execute(Action) match
        // execute(() -> {});
        execute((Runnable) () -> {});
        execute((Action) () -> {});
    }

    public static void execute(Runnable runnable) {
        runnable.run();
    }

    public static void execute(Action action) {
        action.act();
    }

    @FunctionalInterface
    interface Action {
        void act();
    }

    private static void freeVariable() {
        int portNumber = 123;
        Runnable runnable = () -> System.out.println(portNumber);

        // Variable used in lambda expression should be final or effective final
        // portNumber = 3;
    }

}
