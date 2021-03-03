package com.heedi.modernjavainaction.refactoring;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Refactoring {

    // 실행 어라운더
    public static void main(String[] args) {
        try {
            String oneLine = processFile((BufferedReader b) -> b.readLine());
            String twoLine = processFile((BufferedReader b) -> b.readLine() + b.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String processFile(BufferedReaderProcessor processor) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(""))) {
            return processor.process(br);
        }
    }

    @FunctionalInterface
    public interface BufferedReaderProcessor {
        String process(BufferedReader b) throws IOException;
    }
}
