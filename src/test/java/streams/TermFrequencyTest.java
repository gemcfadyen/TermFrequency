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

        List<String> words = termFrequency.execute();

        assertThat(words, containsInAnyOrder("the", "contents", "of", "is", "book", "here", "Another", "line"));
    }

    @Test
    public void tableDoesNotContainDuplicates() throws IOException {
        TermFrequency termFrequency = new TermFrequency(book);

        Collection<String> words = termFrequency.execute();

        assertThat(words.stream().distinct().count(), is((long) words.size()));
    }

    //test ideas:
    //reads more than one line
    //counts the number of times the word has occurred

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
