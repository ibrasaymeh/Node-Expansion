import org.junit.Test;
import static junit.framework.TestCase.assertTrue;
import java.util.ArrayList;
import java.util.List;

public class ExerciseTests {

    @Test
    public void testRange() {

        List<String> expected = new ArrayList<String>();
        expected.add("node1");
        expected.add("node2");
        expected.add("node3");

        List<String> actual = Exercise.evaluate("node[1-3]");

        for (String node : expected) {
            assertTrue(actual.contains(node));
        }

        assertTrue(expected.size() == actual.size());

    }

    @Test
    public void testOptions() {

        List<String> expected = new ArrayList<String>();
        expected.add("node1");
        expected.add("node5");

        List<String> actual = Exercise.evaluate("node[1,5]");

        for (String node : expected) {
            assertTrue(actual.contains(node));
        }

        assertTrue(expected.size() == actual.size());

    }

    @Test
    public void testRangesAndOptionsCombined() {

        List<String> expected = new ArrayList<String>();
        expected.add("c1");
        expected.add("c3");
        expected.add("c4");
        expected.add("c5");

        List<String> actual = Exercise.evaluate("c[1,3-5]");

        for (String node : expected) {
            assertTrue(actual.contains(node));
        }

        assertTrue(expected.size() == actual.size());

    }

    @Test
    public void testMultipleBracketedSections() {

        List<String> expected = new ArrayList<String>();
        expected.add("node1node4");
        expected.add("node1node5");
        expected.add("node2node4");
        expected.add("node2node5");

        List<String> actual = Exercise.evaluate("node[1-2]node[4-5]");

        for (String node : expected) {
            assertTrue(actual.contains(node));
        }

        assertTrue(expected.size() == actual.size());

    }

    @Test
    public void testComplexThreeSectionExample() {

        List<String> expected = new ArrayList<String>();
        expected.add("a1b2c5d");
        expected.add("a1b2c5d");
        expected.add("a1b2c5d");
        expected.add("a1b2c7d");
        expected.add("a1b3c7d");
        expected.add("a1b3c7d");
        expected.add("a1b3c8d");
        expected.add("a1b3c8d");
        expected.add("a1b4c8d");
        expected.add("a1b4c9d");
        expected.add("a1b4c9d");
        expected.add("a1b4c9d");
        expected.add("a3b2c5d");
        expected.add("a3b2c5d");
        expected.add("a3b2c5d");
        expected.add("a3b2c7d");
        expected.add("a3b3c7d");
        expected.add("a3b3c7d");
        expected.add("a3b3c8d");
        expected.add("a3b3c8d");
        expected.add("a3b4c8d");
        expected.add("a3b4c9d");
        expected.add("a3b4c9d");
        expected.add("a3b4c9d");

        List<String> actual = Exercise.evaluate("a[1,3]b[2-4]c[5,7-9]d");

        for (String node : expected) {
            assertTrue(actual.contains(node));
        }

        assertTrue(expected.size() == actual.size());

    }

    @Test
    public void testNodeWithoutValue() {

        List<String> expected = new ArrayList<String>();
        expected.add("a");

        List<String> actual = Exercise.evaluate("a");

        for (String node : expected) {
            assertTrue(actual.contains(node));
        }

        assertTrue(expected.size() == actual.size());

    }

    @Test
    public void testSecondaryNodeWithoutValues() {

        List<String> expected = new ArrayList<String>();
        expected.add("a1a");

        List<String> actual = Exercise.evaluate("a[1]a");

        for (String node : expected) {
            assertTrue(actual.contains(node));
        }

        assertTrue(expected.size() == actual.size());

    }

    @Test
    public void testInconsistentNodeNames() {

        List<String> expected = new ArrayList<String>();
        expected.add("a1ab3");

        List<String> actual = Exercise.evaluate("a[1]ab[3]");

        for (String node : expected) {
            assertTrue(actual.contains(node));
        }

        assertTrue(expected.size() == actual.size());

    }

    @Test
    public void testNameOfNodeAlphabet() {

        List<String> expected = new ArrayList<String>();
        expected.add("a1");

        List<String> actual = Exercise.evaluate("a[1]");

        for (String node : expected) {
            assertTrue(actual.contains(node));
        }

        assertTrue(expected.size() == actual.size());

    }

    @Test
    public void testNameOfNodeNumeric() {

        List<String> expected = new ArrayList<String>();
        expected.add("42");

        List<String> actual = Exercise.evaluate("4[2]");

        for (String node : expected) {
            assertTrue(actual.contains(node));
        }

        assertTrue(expected.size() == actual.size());

    }

    @Test
    public void testNameOfNodeAlphanumeric() {

        List<String> expected = new ArrayList<String>();
        expected.add("4a2");

        List<String> actual = Exercise.evaluate("4a[2]");

        for (String node : expected) {
            assertTrue(actual.contains(node));
        }

        assertTrue(expected.size() == actual.size());

    }

    @Test(expected=IllegalArgumentException.class)
    public void testNameOfNodeSymbols() {

        Exercise.evaluate("[[2]");

    }

    @Test(expected=IllegalArgumentException.class)
    public void testMissingNodeName() {

        Exercise.evaluate("[1]");

    }

    @Test(expected=IllegalArgumentException.class)
    public void testMissingSecondNodeName() {

        Exercise.evaluate("a[1][3]");

    }

    @Test(expected=IllegalArgumentException.class)
    public void testEmptyBracketedSection() {

        Exercise.evaluate("a[]");

    }

    @Test(expected=IllegalArgumentException.class)
    public void testIncompleteBracketedSection() {

        Exercise.evaluate("a[3-");

    }

    @Test(expected=IllegalArgumentException.class)
    public void testEmpty() {

        Exercise.evaluate("");

    }

    @Test(expected=IllegalArgumentException.class)
    public void testNull() {

        Exercise.evaluate(null);

    }

    @Test(expected=IllegalArgumentException.class)
    public void testInvalidMultipleRanges() {

        Exercise.evaluate("a[1-2-3]");

    }

    @Test(expected=NumberFormatException.class)
    public void testNonNumericOption() {

        Exercise.evaluate("a[b]");

    }

    @Test(expected=IllegalArgumentException.class)
    public void testInvalidRangeValues() {

        Exercise.evaluate("a[2-1]");

    }

}
