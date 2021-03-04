package com.heedi.modernjavainaction.refactoring;

import java.util.ArrayList;
import java.util.List;

public class ObserverMain {

    public static void main(String[] args) {
        Feed f = new Feed();
        f.registerObserver(new NYTimes());
        f.registerObserver(new Guardian());
        f.registerObserver(new LeMonde());
        f.notifyObserver("The queen said her favourite book is Modern Java in Action");
    }

    static class NYTimes implements Observer {

        @Override
        public void notify(String tweet) {
            if(tweet != null && tweet.contains("money")) {
                System.out.println("Breaking news in NY ! " + tweet);
            }
        }
    }

    static class Guardian implements Observer {

        @Override
        public void notify(String tweet) {
            if(tweet != null && tweet.contains("queen")) {
                System.out.println("Yet more news from London..." + tweet);
            }
        }
    }

    static class LeMonde implements Observer {

        @Override
        public void notify(String tweet) {
            if(tweet != null && tweet.contains("wine")) {
                System.out.println("Today cheese, wine and news! " + tweet);
            }
        }
    }

    interface Observer {
        void notify(String tweet);
    }

    static class Feed implements Subject {
        private final List<Observer> observers = new ArrayList<>();

        @Override
        public void registerObserver(Observer o) {
            this.observers.add(o);
        }

        @Override
        public void notifyObserver(String tweet) {
            observers.forEach(o -> o.notify(tweet));
        }
    }

    interface Subject {
        void registerObserver(Observer o);
        void notifyObserver(String tweet);
    }
}
