package com.heedi.modernjavainaction.refactoring;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class FactoryMain {

    final static Map<String, Supplier<Product>> map = new HashMap<>();

    static {
        map.put("loan", Loan::new);
        map.put("stock", Stock::new);
        map.put("bond", Bond::new);
    }

    public static void main(String[] args) {
        Product p = ProductFactory.createProduct(" stock");
        System.out.println(p + "의 생산 과정을 클라이언트는 알 수 없다.");

        // 람다 표현식으로 동일한 동작을 할 수 있다.
        Product p2 = createProductBy("stock");
        System.out.println(p2 + "의 생산 과정 또한 클라이언트는 알 수 없다.");
    }

    private static Product createProductBy(String name) {
        Supplier<Product> productSupplier = map.get(name);
        Product p = productSupplier.get();

        if(p != null) return p;
        throw new IllegalArgumentException("No such product " + name);
    }

    public static class ProductFactory {
        public static Product createProduct(String name) {
            switch (name) {
                case " loan" : return new Loan();
                case " stock" : return new Stock();
                case " bond" : return new Bond();
                default: throw new RuntimeException(" No such product " + name);
            }
        }

    }

    public static class Product {
    }
    private static class Loan extends Product {
    }
    private static class Stock extends Product {
    }
    private static class Bond extends Product {
    }

}
