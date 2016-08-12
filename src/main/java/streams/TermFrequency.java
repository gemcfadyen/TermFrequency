package streams;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TermFrequency {
    private File source;
    private File stopWords;

    public static void main(String... args) throws IOException {
        String pathToBook = new File(".").getCanonicalPath() + "/src/main/resources/pride-and-prejudice.txt";
        String pathToStopWords = new File(".").getCanonicalPath() + "/src/main/resources/stop-words.txt";

        TermFrequency termFrequency = new TermFrequency(new File(pathToBook), new File(pathToStopWords));
        Map<String, Integer> results = termFrequency.execute();

        System.out.println("Results: ");
        for (Map.Entry<String, Integer> entry : results.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public TermFrequency(File source, File stopWords) {
        this.source = source;
        this.stopWords = stopWords;
    }

    public Map<String, Integer> execute() throws IOException {
        Map<String, Integer> wordCounts = new HashMap<>();

        Stream<String> lineOfStopWords = Files.lines(Paths.get(stopWords.getAbsolutePath()));

        List<String> wordsToOmit =
                lineOfStopWords.map(s -> s.split(","))
                        .flatMap(Arrays::stream)
                        .map(String::toLowerCase)
                        .collect(Collectors.toList());

        Files.lines(Paths.get(source.getAbsolutePath()))
                .map(s -> s.split(" "))
                .flatMap(Arrays::stream)
                .map(String::toLowerCase)
                .filter(s -> !wordsToOmit.contains(s))
                .forEach(s -> addToTable(wordCounts, s));

        return wordCounts;
    }

    private void addToTable(Map<String, Integer> table, String word) {
        if (!table.containsKey(word)) {
            table.put(word, 1);
        } else {
            table.put(word, incrementOccurrancesOf(table.get(word)));
        }
    }

    private int incrementOccurrancesOf(Integer count) {
        return count + 1;
    }
}
