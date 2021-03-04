package com.heedi.modernjavainaction.refactoring;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ChainOfResponsibilityMain {

    public static void main(String[] args) {
        ProcessingObject<String> p1 = new HeaderTextProcessing();
        ProcessingObject<String> p2 = new SpellCheckProcessing();

        p1.setSuccessor(p2);
        String result = p1.handle("Aren't coockie really lovely?!!");
        System.out.println(result);

        // UnaryOperator 을 이용해 동일하게 구현이 가능하다.
        UnaryOperator<String> headerProcessing = (String text) -> "From Soul, MoMo and LaLa : " + text;
        UnaryOperator<String> spellCheckProcessing = (String text) -> text.replaceAll("coockie", "cookie");
        Function<String, String> pipeLine = headerProcessing.andThen(spellCheckProcessing);

        String sameResult = pipeLine.apply("Aren't coockie really lovely?!!");
        System.out.println(sameResult);
    }

    private static class HeaderTextProcessing extends ProcessingObject<String> {
        @Override
        protected String handleWork(String text) {
            return "From Soul, MoMo and LaLa : " + text;
        }
    }

    private static class SpellCheckProcessing extends ProcessingObject<String> {
        @Override
        protected String handleWork(String text) {
            return text.replaceAll("coockie", "cookie");
        }
    }

    public static abstract class ProcessingObject<T> {
        protected ProcessingObject<T> successor;

        public void setSuccessor(ProcessingObject<T> successor) {
            this.successor = successor;
        }

        public T handle(T input) {
            T r = handleWork(input);

            if (successor != null) {
                return successor.handle(r);
            }
            return r;
        }

        protected abstract T handleWork(T input);
    }
}
