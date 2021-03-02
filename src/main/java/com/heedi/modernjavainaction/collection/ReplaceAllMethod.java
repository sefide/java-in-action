package com.heedi.modernjavainaction.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class ReplaceAllMethod {

    public static void main(String[] args) {
        List<String> referenceCodes = new ArrayList<>();
        referenceCodes.add("a12");
        referenceCodes.add("C14");
        referenceCodes.add("b13");

        // 새로운 리스트 생성 후 출력
        referenceCodes.stream()
                .map(code -> Character.toUpperCase(code.charAt(0)) +
                        code.substring(1))
                .collect(Collectors.toList())
                .forEach(System.out::println);

        // ListIterator 객체를 이용해 값 변경
        for (ListIterator<String> iterator = referenceCodes.listIterator();
             iterator.hasNext(); ) {
            String code = iterator.next();
            // 값 변경
            iterator.set(Character.toUpperCase(code.charAt(0)) + code.substring(1));
        }

        // replaceAll 메서드로 간단하게 값 변경
        referenceCodes.replaceAll(code -> Character.toUpperCase(code.charAt(0)) + code.substring(1));
        System.out.println(referenceCodes);

        // sort 정렬 수행
        referenceCodes.sort(String::compareTo);
        System.out.println(referenceCodes);
    }
}
