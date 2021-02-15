package com.heedi.modernjavainaction.lambda;

@FunctionalInterface
public interface TriFunction<T, U, S, R> {
    R apply(T t, U u, S s);
}
