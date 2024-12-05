package components.simplesearchengine;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import components.map.Map.Pair;
import components.simplereader.SimpleReader1L;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Saurav Poudyel
 *
 */
public abstract class SimpleSearchEngineTest {

    /**
     * Invokes the appropriate {@code SimpleSearchEngine} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new {@code SimpleSearchEngine1L}
     * @ensures constructorTest = (true, {})
     */
    protected abstract SimpleSearchEngine1L<String> constructorTest();

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SimpleSearchEngine1L<String> constructorRef();

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SimpleSearchEngine1L<String> createFromArgsTest(
            boolean insertionMode, String... args) {
        assert ((args.length)
                % 2) == 0 : "Violation of: Each tag has a respective value in @this.entries; num arguments should be even";

        SimpleSearchEngine1L<String> sse = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            sse.add(args[i], args[i + 1]);
        }
        if (!insertionMode) {
            sse.changeToSearchMode();
        }
        return sse;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SimpleSearchEngine1L<String> createFromArgsRef(
            boolean insertionMode, String... args) {
        assert ((args.length)
                % 2) == 0 : "Violation of: Each tag has a respective value in @this.entries; num arguments should be even";

        SimpleSearchEngine1L<String> sse = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            sse.add(args[i], args[i + 1]);
        }
        if (!insertionMode) {
            sse.changeToSearchMode();
        }
        return sse;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type from a document with the given entries and
     * mode.
     *
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SimpleSearchEngine1L<String> createFromDocTest(
            boolean insertionMode, String inputFile) {
        assert inputFile != null : "Violation of: input file is not null";

        SimpleSearchEngine1L<String> sse = this.constructorTest();
        SimpleReader1L input = new SimpleReader1L(inputFile);

        int entryNum = 0;
        while (!input.atEOS()) {
            String entry = input.nextLine();
            sse.add(entry.toString(), Integer.toString(entryNum));
            entryNum++;
        }

        if (!insertionMode) {
            sse.changeToSearchMode();
        }
        return sse;
    }

    /*
     * Search Engine for "Lots" and "Some" Test cases
     */
    private SimpleSearchEngine1L<String> allSSE = this.createFromDocTest(false,
            "test\\components\\simplesearchengine\\testData\\AllWords.txt");
    private int allEntries = this.allSSE.size();

    /*
     * Simple test cases.
     */

    @Test
    public final void testIsInInsertionMode() {
        SimpleSearchEngine1L<String> sse = this.constructorTest();

        assertEquals(true, sse.isInInsertionMode());
    }

    @Test
    public final void testChangeToSearchMode() {
        SimpleSearchEngine1L<String> sse = this.constructorTest();

        sse.changeToSearchMode();

        assertEquals(false, sse.isInInsertionMode());
    }

    @Test
    public final void testSizeEmpty() {
        SimpleSearchEngine1L<String> sse = this.constructorTest();

        assertEquals(0, sse.size());
    }

    @Test
    public final void testSizeOneEntry() {
        SimpleSearchEngine1L<String> sse = this.createFromArgsTest(true, "a",
                "b");

        assertEquals(1, sse.size());
    }

    @Test
    public final void testSizeMultEntry() {
        SimpleSearchEngine1L<String> sse = this.createFromArgsTest(true, "a",
                "b", "c", "d", "e", "f", "g", "h");

        assertEquals(4, sse.size());
    }

    @Test
    public final void testValueOf() {
        SimpleSearchEngine1L<String> sse = this.createFromArgsTest(true, "cat",
                "1", "dog", "2");
        assertEquals("1", sse.valueOf("cat"));
    }

    @Test
    public final void testContains() {
        SimpleSearchEngine1L<String> sse = this.createFromArgsTest(true, "cat",
                "1", "dog", "2");
        assertEquals(true, sse.contains("cat"));
    }

    /*
     * Add and remove test cases
     */
    @Test
    public final void testAddSingleEntry() {
        SimpleSearchEngine1L<String> sse = this.constructorTest();
        sse.add("cat", "1");
        assertEquals(1, sse.size());
    }

    @Test
    public final void testRemoveKey() {
        SimpleSearchEngine1L<String> sse = this.createFromArgsTest(true, "cat",
                "1", "dog", "2");
        sse.remove("cat");
        assertEquals(1, sse.size());
        assertEquals(false, sse.contains("cat"));
    }

    @Test
    public final void testRemoveAnyNonEmpty() {
        SimpleSearchEngine1L<String> sse = this.createFromArgsTest(true, "cat",
                "1", "dog", "2");
        Pair<String, String> removed = sse.removeAny();
        assertEquals(1, sse.size()); // Size should decrease
        assertEquals(false, sse.contains(removed.key())); // Key should no longer be present
    }

    /*
     * Tests for toString methods
     */
    @Test
    public final void testToStringEmptyInsertionMode() {
        SimpleSearchEngine1L<String> sse = this.constructorTest();

        String sseString = sse.toString();
        String sseStringExpected = "(true, ())";

        assertEquals(sseStringExpected, sseString);
    }

    public final void testToStringEmptySearchMode() {
        SimpleSearchEngine1L<String> sse = this.constructorTest();
        sse.changeToSearchMode();

        String sseString = sse.toString();
        String sseStringExpected = "(false, ())";

        assertEquals(sseStringExpected, sseString);
    }

    @Test
    public final void testToStringInsertionMode() {
        SimpleSearchEngine1L<String> sse = this.createFromArgsTest(true, "a",
                "b", "c", "d", "e", "f", "g", "h");

        String sseString = sse.toString();
        String sseStringExpected = "(true, ((a, b), (c, d), (e, f), (g, h))";

        assertEquals(sseStringExpected, sseString);
    }

    @Test
    public final void testToStringSearchMode() {
        SimpleSearchEngine1L<String> sse = this.createFromArgsTest(false, "a",
                "b", "c", "d", "e", "f", "g", "h");

        String sseString = sse.toString();
        String sseStringExpected = "(false, ((a, b), (c, d), (e, f), (g, h))";

        assertEquals(sseStringExpected, sseString);
    }

    /*
     * Tests for equals method
     */
    @Test
    public final void testEqualsInsertionMode() {
        SimpleSearchEngine1L<String> sse = this.createFromArgsTest(true, "a",
                "b", "c", "d", "e", "f", "g", "h");
        SimpleSearchEngine1L<String> sse2 = this.createFromArgsTest(true, "a",
                "b", "c", "d", "e", "f", "g", "h");

        assertEquals(sse, sse2);
    }

    @Test
    public final void testEqualsSearchMode() {
        SimpleSearchEngine1L<String> sse = this.createFromArgsTest(false, "a",
                "b", "c", "d", "e", "f", "g", "h");
        SimpleSearchEngine1L<String> sse2 = this.createFromArgsTest(false, "a",
                "b", "c", "d", "e", "f", "g", "h");

        assertEquals(sse, sse2);
    }

    /*
     * Add and remove tests
     */
    @Test
    public final void testAddInEmpty() {
        SimpleSearchEngine1L<String> sse = this.constructorTest();

        sse.add("bat", "1");

        assertEquals(1, sse.size());
    }

    /*
     * Prefix Search Tests
     */
    @Test
    public final void testPrefixSearchBasic() {
        SimpleSearchEngine1L<String> sse = this.createFromArgsTest(false, "bat",
                "1", "base", "2", "ball", "3", "tree", "4");

        List<String> expectedVal = Arrays.asList("ball", "base", "bat");
        List<String> val = sse.prefixSearch("ba");

        assertEquals(expectedVal, val);
    }

    @Test
    public final void testPrefixSearchOneLetter() {
        SimpleSearchEngine1L<String> sse = this.createFromArgsTest(false, "bat",
                "1", "base", "2", "ball", "3", "tree", "4", "bomba", "5");

        List<String> expectedVal = Arrays.asList("ball", "base", "bat",
                "bomba");
        List<String> val = sse.prefixSearch("b");

        assertEquals(expectedVal, val);
    }

    @Test
    public final void testPrefixSearchForLexigraphicalOrder() {
        SimpleSearchEngine1L<String> sse = this.createFromArgsTest(false, "baa",
                "1", "bac", "2", "bab", "3", "tree", "4", "bae", "5", "baz",
                "6");

        List<String> expectedVal = Arrays.asList("baa", "bab", "bac", "bae",
                "baz");
        List<String> val = sse.prefixSearch("b");

        assertEquals(expectedVal, val);
    }

    @Test
    public final void testPrefixSearchWithEmpty() {
        SimpleSearchEngine1L<String> sse = this.createFromArgsTest(false, "baa",
                "1", "bac", "2", "bab", "3", "tree", "4", "bae", "5", "baz",
                "6");

        List<String> expectedVal = Arrays.asList("baa", "bab", "bac", "bae",
                "baz", "tree");
        List<String> val = sse.prefixSearch("");

        assertEquals(expectedVal, val);
    }

    @Test
    public final void testPrefixSearchAll() {

        List<String> expectedVal = Arrays.asList("bask", "basket", "basketball",
                "basketry", "basketweaver", "basking");

        long startTime = System.nanoTime(); // Start timing
        List<String> val = this.allSSE.prefixSearch("bask"); // Call
        long endTime = System.nanoTime(); // End timing

        float duration = (endTime - startTime) / 1_000_000;
        System.out.println("Prefix Search on All for 'bask': ");
        System.out.println("Entries: " + this.allEntries);
        System.out.println("Execution time: " + duration + " ms \n");

        assertEquals(expectedVal, val);
    }

    /*
     * Contains Search Tests
     */
    @Test
    public final void testContainsSearchBasic() {
        SimpleSearchEngine1L<String> sse = this.createFromArgsTest(false, "bat",
                "1", "base", "2", "ball", "3", "taste", "4");

        List<String> expectedVal = Arrays.asList("base", "taste");
        List<String> val = sse.containsSearch("as");

        assertEquals(expectedVal, val);
    }

    @Test
    public final void testContainsSearchNonExistentSubstring() {
        SimpleSearchEngine1L<String> sse = this.createFromArgsTest(false, "bat",
                "1", "base", "2", "ball", "3", "tree", "4");

        List<String> expectedVal = Arrays.asList(); // Empty list
        List<String> val = sse.containsSearch("xyz");

        assertEquals(expectedVal, val);
    }

    @Test
    public final void testContainsSearchOneLetter() {
        SimpleSearchEngine1L<String> sse = this.createFromArgsTest(false, "bat",
                "1", "base", "2", "bound", "3", "tree", "4", "bomba", "5",
                "ball", "5");

        List<String> expectedVal = Arrays.asList("ball", "base", "bat",
                "bomba");
        List<String> val = sse.containsSearch("a");

        assertEquals(expectedVal, val);
    }

    @Test
    public final void testContainsSearchForLexigraphicalOrder() {
        SimpleSearchEngine1L<String> sse = this.createFromArgsTest(false, "baa",
                "1", "bac", "2", "bab", "3", "tree", "4", "aab", "5", "baz",
                "6");

        List<String> expectedVal = Arrays.asList("aab", "baa", "bab", "bac",
                "baz");
        List<String> val = sse.containsSearch("b");

        assertEquals(expectedVal, val);
    }

    @Test
    public final void testContainsSearchAll() {

        List<String> expectedVal = Arrays.asList("architectural",
                "architecturally", "architecture", "cytoarchitectural",
                "cytoarchitecture");

        long startTime = System.nanoTime(); // Start timing
        List<String> val = this.allSSE.containsSearch("architectu"); // Call
        long endTime = System.nanoTime(); // End timing

        long duration = (endTime - startTime) / 1_000_000;
        System.out.println("Contains Search on Some for 'architectu': ");
        System.out.println("Entries: " + this.allEntries);
        System.out.println("Execution time: " + duration + " ms \n");

        assertEquals(expectedVal, val);
    }

    /*
     * Relative Search Tests
     */
    @Test
    public final void testRelativeSearchBasic() {
        SimpleSearchEngine1L<String> sse = this.createFromArgsTest(false, "bat",
                "1", "base", "2", "ball", "3", "taste", "4");

        String expectedVal = "bat";
        String val = sse.relativeSearch("batt");

        assertEquals(expectedVal, val);
    }

    @Test
    public final void testRelativeSearchOneLetter() {
        SimpleSearchEngine1L<String> sse = this.createFromArgsTest(false, "bat",
                "1", "base", "2", "bound", "3", "tree", "4", "bomba", "5",
                "ball", "5");

        String expectedVal = "bat";
        String val = sse.relativeSearch("b");

        assertEquals(expectedVal, val);
    }

    @Test
    public final void testRelativeSearchForLexigraphicalOrder() {
        SimpleSearchEngine1L<String> sse = this.createFromArgsTest(false, "baa",
                "1", "bac", "2", "bab", "3", "tree", "4", "bae", "5", "baz",
                "6");

        String expectedVal = "baa";
        String val = sse.relativeSearch("a");

        assertEquals(expectedVal, val);
    }

    @Test
    public final void testRelativeSearchAllMultTypo() {
        String expectedVal = "traditionally";

        long startTime = System.nanoTime(); // Start timing
        String val = this.allSSE.relativeSearch("trraditunully"); // Call
        long endTime = System.nanoTime(); // End timing

        long duration = (endTime - startTime) / 1_000_000;
        System.out.println(
                "Relative Search on All with 'trraditunully' for 'traditionally': ");
        System.out.println("Entries: " + this.allEntries);
        System.out.println("Execution time: " + duration + " ms \n");

        assertEquals(expectedVal, val);
    }

}
