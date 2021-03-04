package com.heedi.modernjavainaction.refactoring;

import java.util.function.Consumer;

public class TemplateMethodMain {

    public static void main(String[] args) {
        OnlineBanking sinNan = new SinNanBanking();
        sinNan.processCustomer(33);
        OnlineBanking cookMan = new CookManBanking();
        cookMan.processCustomer(44);

        // OnlineBanking 상속할 필요 없이 람다로 동일한 동작을 수행할 수 있다.
        processCustomer(77, (Customer c) -> System.out.println(c.getName() + ", something happy !"));
    }

    public static void processCustomer(int id, Consumer<Customer> makeCustomerHappy) {
        Customer c = Database.getCustomerWithId(id);
        makeCustomerHappy.accept(c);
    }

    abstract static class OnlineBanking {
        public void processCustomer(int id) {
            Customer c = Database.getCustomerWithId(id);
            makeCustomerHappy(c);
        }

        // 은행마다 원하는 동작을 구현
        abstract void makeCustomerHappy(Customer c);
    }

    static class SinNanBanking extends OnlineBanking {

        @Override
        void makeCustomerHappy(Customer c) {
            System.out.println(c.getName() + ", make some noise ~");
        }
    }

    static class CookManBanking extends OnlineBanking {

        @Override
        void makeCustomerHappy(Customer c) {
            System.out.println(c.getName() + ", let's meat ~ ~~ ");
        }
    }

    private static class Customer {
        private final String name;

        public Customer(int id) {
            this.name = id + "님";
        }

        public String getName() {
            return name;
        }
    }

    private static class Database {
        static Customer getCustomerWithId(int id) {
            return new Customer(id);
        }
    }
}
