package streams;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TermFrequencyTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private File book;
    private File stopWords;

    @Before
    public void setup() throws IOException {
        book = writeContentOfBook();
        stopWords = writeStopWords();
    }

    @Test
    public void returnsTableOfWords() throws IOException {
        TermFrequency termFrequency = new TermFrequency(book);

        Collection<String> words = termFrequency.execute().keySet();

        assertThat(words, containsInAnyOrder("the", "contents", "of", "is", "book", "here", "Another", "line"));
    }

    @Test
    public void tableDoesNotContainDuplicates() throws IOException {
        TermFrequency termFrequency = new TermFrequency(book);

        Collection<String> words = termFrequency.execute().keySet();

        assertThat(words.stream().distinct().count(), is((long) words.size()));
    }

    @Test
    public void countsTheNumberOfTimesAWordOccurrs() throws IOException {
        TermFrequency termFrequency = new TermFrequency(book);

        Map<String, Integer> wordCounts = termFrequency.execute();

        assertThat(wordCounts.get("Another"), is(1));
        assertThat(wordCounts.get("the"), is(2));
    }


    //test ideas:
    //filter out the stop words

    private File writeStopWords() throws IOException {
        File stopFile = temporaryFolder.newFile("stop-words.txt");
        Writer stopWordsWriter = new FileWriter(stopFile);
        stopWordsWriter.write("the, is,");
        return stopFile;
    }

    private File writeContentOfBook() throws IOException {
        File book = temporaryFolder.newFile("source.txt");
        Writer fileWriter = new FileWriter(book);
        fileWriter.write("the contents of the book is here\nAnother line");
        fileWriter.flush();
        return book;
    }
}
