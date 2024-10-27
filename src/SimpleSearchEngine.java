import java.util.Comparator;

import components.map.Map;
import components.map.Map4;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Takes in words into an index represented as a Map and is able to skim through
 * it to return a Queue of words that contain another keyWord
 *
 * !!!!!! I Wrote this pretty quickly because it's a proof of concept but the
 * algorithms that are implemented are kinda hot garbage rn, they'll be fixed
 * and be better in due time
 *
 * @author Saurav Poudyel
 *
 */
public final class SimpleSearchEngine {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Elements included in {@code this}.
     */
    private Map<String, Integer> wordCountMap;

    /**
     * Compare {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareToIgnoreCase(o2);
        }
    }

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.wordCountMap = new Map4<>();
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public SimpleSearchEngine() {
        this.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    public void add(String word) {
        assert word != null : "Violation of: x is not null";

        /*
         * If the word wasn't in the map, add the word to the map repersentation
         * and make it's occurence 1
         */
        if (!this.wordCountMap.hasKey(word)) {
            this.wordCountMap.add(word, 1);
        } else {
            /*
             * If the word was in the map, increment the occurence of it
             */
            this.wordCountMap.replaceValue(word,
                    (this.wordCountMap.value(word) + 1));
        }
    }

    public void remove(String word) {
        assert word != null : "Violation of: word is not null";
        assert this.wordCountMap.hasKey(word) : "Violation of: word is in this";

        /*
         * If the word is in the map >1 times, remove an occurence of word
         */
        if (this.wordCountMap.value(word) > 1) {
            this.wordCountMap.replaceValue(word,
                    (this.wordCountMap.value(word) - 1));
        } else {
            /*
             * If the word is in the map <1 times, remove the word
             */
            this.wordCountMap.remove(word);
        }
    }

    public void removeAll(String word) {
        assert word != null : "Violation of: word is not null";
        assert this.wordCountMap.hasKey(word) : "Violation of: word is in this";

        /*
         * Remove all occurences of a word no matter what
         */
        this.wordCountMap.remove(word);

    }

    public boolean isInIndex(String word) {
        assert word != null : "Violation of: word is not null";

        /*
         * Return if the index has the word
         */
        return this.wordCountMap.hasKey(word);

    }

    public int frequencyOf(String word) {
        assert word != null : "Violation of: word is not null";

        /*
         * Return the mapped frequency of a word
         */
        return this.wordCountMap.value(word);

    }

    /*
     * In Class methods -------------------------------------------------------
     */

    /**
     * Iterates through the @fileName and sends each word from it
     * to @wordCountMap to get indexed
     *
     * @param fileName
     *            The name of the stream that provides all the words ands lines
     *            that the program reads
     * @updates this.wordCountMap
     * @requires for all (words: fileName) wordCountMap = #wordCountMap + (words
     *           : fileName, word counts : fileName)
     */
    public void addFromDocument(String fileName) {
        assert fileName != null : "Violation of fileName is not null";

        SimpleReader inFile = new SimpleReader1L(fileName);

        /*
         * Create the set of word seperators
         */
        Set<Character> seperatorSet = new Set1L<>();
        seperatorSet.add(' ');
        seperatorSet.add('!');
        seperatorSet.add('.');
        seperatorSet.add(',');
        seperatorSet.add('?');
        seperatorSet.add(')');
        seperatorSet.add('"');
        seperatorSet.add('\n');
        seperatorSet.add('\t');
        seperatorSet.add('-');
        seperatorSet.add('_');

        /*
         * Iterate through the entire input file and store each line into a
         * String and then iterate through that to get each word
         */
        while (!inFile.atEOS()) {
            /*
             * Store the lines into a String and create positions to subdivide
             * the line into words by the reference seperatorSet
             */
            String curLine = inFile.nextLine();

            int lastWordPos = 0;
            int curPos = 0;
            /*
             * Iterate through the line and cut each word into it's own String
             */
            while (curPos < curLine.length()) {
                if (seperatorSet.contains(curLine.charAt(curPos))
                        || curPos == curLine.length()) {
                    /*
                     * Seperate out a word using the bounds, and then set the
                     * lastWordPos to the current end bound + 1.
                     */
                    String curWord = curLine.substring(lastWordPos, curPos);
                    lastWordPos = curPos + 1;

                    /*
                     * Send each word to get mapped per it's occurence.
                     */
                    if (curWord.length() > 0) {
                        this.add(curWord.toLowerCase());
                    }
                }
                curPos++;
            }
        }
    }

    /**
     * Iterates through the index and searches for words that have the prefix of
     * the keyword
     *
     * @param keyWord
     *            The prefix word to search for in the index
     * @ensures Queue that is returned contains words : index that have the form
     *          keyword + rest of the word
     */
    public Queue<String> prefixSearch(String keyWord) {
        assert keyWord != null : "Violation of keyWord is not null";

        Queue<String> returnQueue = new Queue1L<>();

        /*
         * Iterate through the wordCountMap and add all the words with the
         * prefix to a queue
         */
        Map<String, Integer> wordCountMapTemp = this.wordCountMap.newInstance();
        int mapSize = this.wordCountMap.size();
        wordCountMapTemp.transferFrom(this.wordCountMap);

        for (int mapPos = 0; mapPos < mapSize; mapPos++) {
            /*
             * Get a random pair from the temp map, check if it has the proper
             * pfrefix and add it to the queue if it does
             */
            Map.Pair<String, Integer> tempPair = wordCountMapTemp.removeAny();
            if (tempPair.key().length() >= keyWord.length()) {
                if (tempPair.key().substring(0, keyWord.length())
                        .equals(keyWord)) {
                    returnQueue.enqueue(tempPair.key());
                }
            }
            this.wordCountMap.add(tempPair.key(), tempPair.value());
        }

        return returnQueue;
    }

    /**
     * Iterates through the index and searches for words that have the prefix of
     * the keyword
     *
     * @param keyWord
     *            The keyword to search for in the index
     * @ensures Queue that is returned contains words : index that contain the
     *          keyword
     */
    public Queue<String> containSearch(String keyWord) {
        assert keyWord != null : "Violation of keyWord is not null";

        Queue<String> returnQueue = new Queue1L<>();

        /*
         * Iterate through the wordCountMap and add all the words with the
         * prefix to a queue
         */
        Map<String, Integer> wordCountMapTemp = this.wordCountMap.newInstance();
        int mapSize = this.wordCountMap.size();
        wordCountMapTemp.transferFrom(this.wordCountMap);

        for (int mapPos = 0; mapPos < mapSize; mapPos++) {
            /*
             * Get a random word from the temp map, check if it has the keyword
             * if it does, then add it to the queue.
             */
            Map.Pair<String, Integer> tempPair = wordCountMapTemp.removeAny();
            if (tempPair.key().length() >= keyWord.length()) {
                /*
                 * Iterate through the temp word to try and find the keyword
                 */
                for (int i = 0; i < tempPair.key().length(); i++) {
                    /*
                     * Check if we can even access the last few digits to not
                     * cause an index out of bounds error and then check if a
                     * substring of the size of the keyword within the temp word
                     * equals that keyword
                     */
                    if ((i + keyWord.length() <= tempPair.key().length())
                            && (tempPair.key()
                                    .substring(i, i + keyWord.length())
                                    .equals(keyWord))) {
                        returnQueue.enqueue(tempPair.key());
                    }
                }
            }
            this.wordCountMap.add(tempPair.key(), tempPair.value());
        }

        return returnQueue;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        /*
         * Get the word data for the search engine from a document
         */
        SimpleSearchEngine searchEngine = new SimpleSearchEngine();
        searchEngine.addFromDocument("src\\data\\words.txt");

        /*
         * Get all the words that start with "th"
         */
        Queue<String> thContainSearchList = searchEngine.containSearch("th");
        Queue<String> thPrefixSearchList = searchEngine.prefixSearch("th");

        /*
         * Iterate through the list of words with "th" and print it out
         */
        Queue<String> tempQ = thContainSearchList.newInstance();
        tempQ.transferFrom(thContainSearchList);

        Comparator<String> alphabetize = new SimpleSearchEngine.StringLT();
        tempQ.sort(alphabetize); // alphabetize the queue for fun

        out.println("-------CONTAIN SEARCH FOR TH-------");
        while (tempQ.length() > 0) {
            String tempString = tempQ.dequeue();
            out.print(tempString + '\n');
            thContainSearchList.enqueue(tempString);
        }

        /*
         * Iterate through the list of words with "th" as prefix and print it
         * out
         */
        tempQ = thPrefixSearchList.newInstance();
        tempQ.transferFrom(thPrefixSearchList);

        out.println("-------PREFIX SEARCH FOR TH-------");
        while (tempQ.length() > 0) {
            String tempString = tempQ.dequeue();
            out.print(tempString + '\n');
            thPrefixSearchList.enqueue(tempString);
        }
    }
}
