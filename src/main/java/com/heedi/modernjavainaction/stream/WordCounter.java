package com.heedi.modernjavainaction.stream;

public class WordCounter {
    private final int counter;
    private final boolean lastSpace;

    public WordCounter(int counter, boolean lastSpace) {
        this.counter = counter;
        this.lastSpace = lastSpace;
    }

    /**
     * 문자열의 문자를 하나씩 탐색하여
     * 탐색하는 문자가 공백인 경우, 이전에 탐색한 문자 공백 여부(lastSpace)가 false이면 true로 변경,
     * 공백이 아닌 경우, 이전에 탐색한 문자 공백 여부(lastSpace)에 따라 counter 개수 증가
     *
     * @param c 탐색 문자
     * @return 현재까지 탐색된 WordCounter 반환
     */
    public WordCounter accumulate(Character c) {
        if (Character.isWhitespace(c)) {
            return lastSpace ?
                    this :
                    new WordCounter(counter, true); // 공백 여부를 true로 반환
        } else {
            return lastSpace ?
                    new WordCounter(counter + 1, false) :  // 단어 하나가 끝난 경우 counter 개수 증가
                    this;
        }
    }

    /**
     * 두 WordCounter의 counter 개수를 합친다.
     *
     * @param wordCounter 병합할 WordCounter
     * @return 병합된 WordCounter
     */
    public WordCounter combine(WordCounter wordCounter) {
        return new WordCounter(counter + wordCounter.counter, wordCounter.lastSpace);
    }

    /**
     * @return
     */
    public int getCounter() {
        return counter;
    }
}
