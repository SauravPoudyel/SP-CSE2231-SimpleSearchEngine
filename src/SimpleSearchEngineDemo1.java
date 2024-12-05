import java.util.Scanner;

import components.simplereader.SimpleReader1L;
import components.simplesearchengine.SimpleSearchEngine1L;

/**
 * A quick demo on class SimpleSearchEngine
 *
 * @author Saurav Poudyel
 *
 */
public class SimpleSearchEngineDemo1 {

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
    public static SimpleSearchEngine1L<String> createFromArgsTest(
            boolean insertionMode, String... args) {
        assert ((args.length)
                % 2) == 0 : "Violation of: Each tag has a respective value in @this.entries; num arguments should be even";

        SimpleSearchEngine1L<String> sse = new SimpleSearchEngine1L<>();
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
    public static SimpleSearchEngine1L<String> createFromDocTest(
            boolean insertionMode, String inputFile) {
        assert inputFile != null : "Violation of: input file is not null";

        SimpleSearchEngine1L<String> sse = new SimpleSearchEngine1L<>();
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

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        SimpleSearchEngine1L sse = createFromDocTest(false,
                "test\\components\\simplesearchengine\\testData\\SomeWords.txt");
        sse.changeToSearchMode();

        String quit = "no";
        while (!quit.equals("y")) {
            System.out.println("\nPlease enter a word: ");
            String word = scan.nextLine();
            System.out.println();

            if (sse.contains(word)) {
                System.out.println(
                        "\"" + word + "\" is on line: " + sse.valueOf(word));
            } else {
                String relWord = sse.relativeSearch(word);
                System.out.println("I'm sorry that word is not on the list");
                System.out.println("Did you mean \"" + relWord + "\" on line: "
                        + sse.valueOf(relWord));
            }

            System.out.println("\nquit? y = yes, n = no");
            quit = scan.nextLine();
        }

    }
}
