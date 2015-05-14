package org.trimou.benchmark;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Martin Kouba
 */
public class ExpectedOutputTest {

    @BeforeClass
    public static void beforeClass() {
        Locale.setDefault(Locale.ENGLISH);
    }

    @Test
    public void testBasic() throws IOException {
        Basic benchmark = new Basic();
        benchmark.setup();
        assertOutput("/expected_basic.html", benchmark.render());
    }

    @Test
    public void testBasicHelper() throws IOException {
        BasicHelper benchmark = new BasicHelper();
        benchmark.setup();
        assertOutput("/expected_basic.html", benchmark.render());
    }

    @Test
    public void testPartial() throws IOException {
        BasicPartial benchmark = new BasicPartial();
        benchmark.setup();
        assertOutput("/expected_basic.html", benchmark.render());
    }

    @Test
    public void testInheritance() throws IOException {
        Inheritance benchmark = new Inheritance();
        benchmark.setup();
        assertOutput("/expected_inheritance.html", benchmark.render());
    }

    private void assertOutput(String expectedOutputFile, String actual) throws IOException {
        assertEquals(readExpectedOutputResource(expectedOutputFile), actual.replaceAll("\\s", ""));
    }

    private String readExpectedOutputResource(String expectedOutputFile) throws IOException {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(ExpectedOutputTest.class.getResourceAsStream(expectedOutputFile)))) {
            for (;;) {
                String line = in.readLine();
                if (line == null)
                    break;
                builder.append(line);
            }
        }
        // Remove all whitespaces
        return builder.toString().replaceAll("\\s", "");
    }

}
