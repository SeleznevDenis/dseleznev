package ru.job4j.wait;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test ParallelSearch.
 * @author Denis Seleznev.
 * @version $Id$
 * @since 05.05.2018
 */
public class ParallelSearchTest {

    @Test
    public void resultShouldReturnPathsOfTextFilesExtTxtAndContainedText() {
        List<String> exts = new ArrayList<>(Collections.singletonList("txt"));
        File file = new File(getClass().getClassLoader().getResource("parallelSearch").getFile());
        String root = file.getAbsolutePath();
        File firstExpectFile = new File(getClass().getClassLoader().getResource(
                "parallelSearch/ParallelSearchText.txt"
        ).getFile());
        File secondExpectFile = new File(getClass().getClassLoader().getResource(
                "parallelSearch/deepParallelSearch/DeepParallelSearchText.txt"
        ).getFile());
        List<String> expect = new ArrayList<>(
                asList(firstExpectFile.getAbsolutePath(), secondExpectFile.getAbsolutePath())
        );
        ParallelSearch testSearch = new ParallelSearch(root, "key", exts);
        testSearch.init();
        List<String> result = testSearch.result();
        Collections.sort(result);
        assertThat(result, is(expect));
    }
}