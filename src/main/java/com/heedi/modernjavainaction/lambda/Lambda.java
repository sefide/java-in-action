package com.heedi.modernjavainaction.lambda;

import java.util.concurrent.Callable;
import java.util.function.Predicate;

public class Lambda {

    public static void main(String[] args) {
        Runnable runnable = () -> {};
        Callable<String> callable = () -> "Tricky example ;-)";
        Predicate<String> predicate = (String s) -> true;
    }
}
