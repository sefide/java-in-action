package com.heedi.modernjavainaction.stream;

import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class WordCount {

    public static void main(String[] args) {
        final String SENTENCE = "Hi I'm Heedi,   Studying with Modern Java in Action-!";

        // for문을 이용한 단순 계산
        int wordCount1 = countWordsIteratively(SENTENCE);
        System.out.println("Found " + wordCount1 + " words");

        // WordCounter 이용
        Stream<Character> characterStream1 = IntStream.range(0, SENTENCE.length())
                .mapToObj(SENTENCE::charAt);
        int wordCount2 = countWords(characterStream1);
        System.out.println("Found " + wordCount2 + " words");

        // WordCounter 병렬로 수행
        Stream<Character> characterStream2 = IntStream.range(0, SENTENCE.length())
                .mapToObj(SENTENCE::charAt);
        // 결과 부정확 - 문자열을 임의의 위치를 기준으로 분할하다보니 단어 중복 계산이 들어감
        System.out.println("Found " + countWords(characterStream2.parallel()) + " words");

        // WordCounterSpliterator 이용하여 병렬로 수행
        Spliterator<Character> spliterator = new WordCounterSpliterator(SENTENCE);
        Stream<Character> characterStream3 = StreamSupport.stream(spliterator, true);
        System.out.println("Founds " + countWords(characterStream3) + " words");
    }

    private static int countWords(Stream<Character> stream) {
        WordCounter wordCounter = stream.reduce(new WordCounter(0, true),
                WordCounter::accumulate,
                WordCounter::combine);

        return wordCounter.getCounter();
    }

    public static int countWordsIteratively(String s) {
        int counter = 0;
        boolean lastSpace = true;

        for (char c : s.toCharArray()) {
            if (Character.isWhitespace(c)) {
                lastSpace = true;
            } else {
                if (lastSpace) counter++;
                lastSpace = false;
            }

        }

        return counter;
    }
}
