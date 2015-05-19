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
    public void testDataHierarchy() throws IOException {
        DataHierarchy benchmark = new DataHierarchy();
        benchmark.setup();
        assertOutput("/expected_basic_data_hierarchy.html", benchmark.render());
    }

    @Test
    public void testHelper() throws IOException {
        Helper benchmark = new Helper();
        benchmark.setup();
        assertOutput("/expected_basic.html", benchmark.render());
    }

    @Test
    public void testPartial() throws IOException {
        Partial benchmark = new Partial();
        benchmark.setup();
        assertOutput("/expected_basic.html", benchmark.render());
    }

    @Test
    public void testInheritance() throws IOException {
        TemplateInheritance benchmark = new TemplateInheritance();
        benchmark.setup();
        assertOutput("/expected_inheritance.html", benchmark.render());
    }

    @Test
    public void testDemanding() throws IOException {
        Demanding benchmark = new Demanding();
        benchmark.setup();
        assertOutput("/expected_demanding.html", benchmark.render());
    }

    @Test
    public void testDemandingAsync() throws IOException {
        DemandingAsync benchmark = new DemandingAsync();
        try {
            benchmark.setup();
            assertOutput("/expected_demanding.html", benchmark.render());
            benchmark.tearDown();
        } catch (Exception e) {
            // Ignore
        }
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
