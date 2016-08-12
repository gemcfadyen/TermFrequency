package streams;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class TermFrequencyTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private File book;
    private File stopWords;
    private TermFrequency termFrequency;

    @Before
    public void setup() throws IOException {
        book = writeContentOfBook();
        stopWords = writeStopWords();
        termFrequency = new TermFrequency(book, stopWords);
    }

    @Test
    public void returnsTableOfValidWords() throws IOException {
        Collection<String> words = termFrequency.execute().keySet();

        assertThat(words, containsInAnyOrder("contents", "of", "book", "here", "another", "line"));
    }

    @Test
    public void tableDoesNotContainDuplicates() throws IOException {
        Collection<String> words = termFrequency.execute().keySet();

        assertThat(words.stream().distinct().count(), is((long) words.size()));
    }

    @Test
    public void countsTheNumberOfTimesAWordOccurrs() throws IOException {
        Map<String, Integer> wordCounts = termFrequency.execute();

        assertThat(wordCounts.get("another"), is(1));
        assertThat(wordCounts.get("the"), is(nullValue()));
        assertThat(wordCounts.get("book"), is(2));
        assertThat(wordCounts.get("of"), is(2));
    }

    //TODO
    //Tests required to drill out the following functionality:
    // Strip punctuation from the text
    // Strip numbers from the text
    // Use addOrDefault when incrementing the occurrences


    private File writeStopWords() throws IOException {
        File stopFile = temporaryFolder.newFile("stop-words.txt.txt");
        Writer stopWordsWriter = new FileWriter(stopFile);
        stopWordsWriter.write("the,is,");
        stopWordsWriter.flush();
        return stopFile;
    }

    private File writeContentOfBook() throws IOException {
        File book = temporaryFolder.newFile("source.txt");
        Writer fileWriter = new FileWriter(book);
        fileWriter.write("The contents of the book is here\nAnother line of the book");
        fileWriter.flush();
        return book;
    }
}
