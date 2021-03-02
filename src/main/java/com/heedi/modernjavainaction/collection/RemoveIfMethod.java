package com.heedi.modernjavainaction.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RemoveIfMethod {
    public static List<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {
        transactions.add(new Transaction("1"));
        transactions.add(new Transaction("A"));
        transactions.add(new Transaction("C"));
        transactions.add(new Transaction("2E"));
        transactions.add(new Transaction("99"));

        removeIf();
    }

    private static void removeIf() {
        /*
        // ConcurrentModificationException 발생
        for (Transaction transaction : transactions) {
            if(Character.isDigit(transaction.getReferenceCode().charAt(0))) {
                transactions.remove(transaction);
            }
        }
        */

        /*
        // ConcurrentModificationException 발생
        for(Iterator<Transaction> iterator = transactions.iterator(); iterator.hasNext(); ) {
            Transaction transaction = iterator.next();
            if(Character.isDigit(transaction.getReferenceCode().charAt(0))) {
                transactions.remove(transaction);
            }
        }
        */

        // 정상 삭제됨
        for (Iterator<Transaction> iterator = transactions.iterator(); iterator.hasNext(); ) {
            Transaction transaction = iterator.next();
            if (Character.isDigit(transaction.getReferenceCode().charAt(0))) {
                iterator.remove();
            }
        }

        // removeIf로 간단하게 제거
        transactions.removeIf(transaction -> Character.isDigit(transaction.getReferenceCode().charAt(0)));
        System.out.println(transactions);

    }

    public static class Transaction {
        private final String referenceCode;

        public Transaction(String referenceCode) {
            this.referenceCode = referenceCode;
        }

        public String getReferenceCode() {
            return referenceCode;
        }
    }

}
