package phrolova.parser;

import java.util.stream.IntStream;

public class Parser {

    public int find(String[] words, String target) {
        return IntStream.range(0, words.length)
                .filter(i -> words[i].equals(target))
                .findFirst()
                .orElse(-1);
    }

    public void parse(String message) {
        String[] words = message.split("\\s+");
    }
}
