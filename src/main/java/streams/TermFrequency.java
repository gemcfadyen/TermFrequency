package streams;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TermFrequency {
    private File source;

    public TermFrequency(File source) {
        this.source = source;
    }

    public List<String> execute() throws IOException {
        List<String> allWords = new ArrayList<>();
        //read in contents
        //identify word
        //add to list

        Stream<String> lines = Files.lines(Paths.get(source.getAbsolutePath()));

        Stream<String> wordsInLine = lines.map(s -> {
            return s.split(" ");
        }).flatMap(Arrays::stream);

        wordsInLine.forEach(s -> addToTable(allWords, s));


//                .forEach(s -> allWords.contains(s) ? null : allWords.add(s));
//                .forEach(s -> allWords.addAll(Arrays));
//                .map(s -> allWords.addAll(Arrays.asList(s.split(" ")))).distinct();

        return allWords;
    }

    private void addToTable(List<String> table, String s) {
        if(!table.contains(s)) {
            table.add(s);
        }
    }

    private Function<String, String[]> separateWords(String line) {
        return s -> s.split(" ");
    }
}
