package com.heedi.modernjavainaction.stream;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class FileStream {

    public static void main(String[] args) {
        long uniqueWords = 0;
        Path path = Paths.get("./src/main/java/com/heedi/modernjavainaction/stream/test.txt");

        try (Stream<String> lines = Files.lines(path, Charset.defaultCharset())) {
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct()
                    .count();

            System.out.println(uniqueWords);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
