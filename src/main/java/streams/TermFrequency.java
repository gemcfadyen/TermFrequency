package streams;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TermFrequency {
    private File source;

    public TermFrequency(File source) {
        this.source = source;
    }

    public Map<String, Integer> execute() throws IOException {
        Map<String, Integer> wordCounts = new HashMap<>();
        Stream<String> lines = Files.lines(Paths.get(source.getAbsolutePath()));

        Stream<String> wordsInLine = lines.map(s -> s.split(" ")).flatMap(Arrays::stream);

        wordsInLine.forEach(s -> addToTable(wordCounts, s));

        return wordCounts;
    }

    private void addToTable(Map<String, Integer> table, String word) {
        if (!table.containsKey(word)) {
            table.put(word, 1);
        } else {
            table.put(word, increment(table.get(word), word));
        }
    }

    private int increment(Integer count, String s) {
        return count + 1;
    }
}
