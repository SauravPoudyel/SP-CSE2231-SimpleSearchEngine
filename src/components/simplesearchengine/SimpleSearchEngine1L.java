package components.simplesearchengine;

import java.util.ArrayList;
import java.util.List;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map4;
import components.sequence.Sequence;
import components.sequence.Sequence3;
import components.tree.Tree;
import components.tree.Tree1;

/**
 * {@code SimpleSearchEngine} represented as {@link components.map.Map4} and
 * trie on {@link components.tree.Tree1} with implementations of primary
 * methods.
 *
 * @param <T>
 *            type of {@code SimpleSearchEngine} entries
 *
 * @mathsubtypes <pre>
 * SIMPLE_SEARCH_ENGINE_1L_MODEL is (
 *   insertion_mode: boolean,
 *   entries: finite map of type (String, T),
 *   searchStructure: trie containing sorted letters of type String in entries (String, T)
 *  )
 * </pre>
 * @mathmodel type SimpleSearchEngine1L<T> is modeled by
 *            SIMPLE_SEARCH_ENGINE_1L_MODEL
 *
 * @author Saurav Poudyel
 *
 */
public class SimpleSearchEngine1L<T> extends SimpleSearchEngineSecondary<T> {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Individual Nodes of the Search Structure
     */
    private final static class TrieNode {
        public char nodeChar;
        public boolean isEndOfWord;

        public TrieNode(char nodeChar) {
            this.nodeChar = nodeChar;
            this.isEndOfWord = false;
        }
    }

    /**
     * Insertion mode.
     */
    private boolean insertionMode;

    /**
     * Entries.
     */
    private Map4<String, T> entries;

    /**
     * Search Structure.
     */
    private Tree1<TrieNode> searchStructure;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.insertionMode = true;
        this.entries = new Map4<String, T>();
        this.searchStructure = new Tree1<TrieNode>();
    }

    /*
     * Search Structure; Insertion/Creation Helper Methods ---------------------
     */

    /**
     * Creates a trie from the entries in {@code entries}.
     *
     * @param entries
     *            the map of tags to values to be added to the trie
     * @requires entries != null
     * @return A trie containing all tags from {@code entries}
     * @ensures The returned tree has all tags from {@code entries} as paths
     *          from root to nodes in the trie
     */
    private Tree1<TrieNode> createTrie(Map<String, T> entries) {
        assert entries != null : "Violation of: entries is not null";

        Tree1<TrieNode> Trie = new Tree1<>();
        TrieNode rootTreeNode = new TrieNode(' ');
        Trie.assemble(rootTreeNode, new Sequence3<Tree<TrieNode>>());

        /*
         * Iterate through the map and add the tags in form {String} to the Trie
         */
        Map4<String, T> tempEntries = new Map4<>();
        tempEntries.transferFrom(entries);
        while (tempEntries.size() > 0) {
            Pair<String, T> tempPair = tempEntries.removeAny();
            this.insertTagInTrie(Trie, tempPair.key());
            entries.add(tempPair.key(), tempPair.value());
        }

        return Trie;
    }

    /**
     * Inserts each character of a tag into the trie.
     *
     * @param tree
     *            the trie into which the tag will be inserted
     * @param tag
     *            the tag to insert
     * @requires tree != null and tag != null
     * @ensures the trie will contain all characters of {@code tag} as a
     *          sequence of nodes
     */
    private void insertTagInTrie(Tree1<TrieNode> tree, String tag) {
        /*
         * Iterate through the tag and insert each char in the Trie
         */
        Tree1<TrieNode> currentNode = tree;
        for (char tagChar : tag.toCharArray()) {
            currentNode = this.insertCharInTrie(currentNode, tagChar);
        }

        /*
         * At the end of the tag, mark the char node as the end of word
         */
        TrieNode endNode = currentNode.root();
        endNode.isEndOfWord = true;
        currentNode.replaceRoot(endNode); // Update the node in the tree
    }

    /**
     * Inserts a character into the trie, maintaining lexicographical order.
     *
     * @param currentNode
     *            the current node in the trie
     * @param insertChar
     *            the character to insert
     * @requires currentNode != null
     * @return The node in the trie that corresponds to the inserted character
     *         {@code ch}
     * @ensures The trie will have {@code ch} inserted in the correct
     *          lexicographical position in {@code currentNode}
     */
    private Tree1<TrieNode> insertCharInTrie(Tree1<TrieNode> currentNode,
            char insertChar) {

        Sequence<Tree<TrieNode>> children = currentNode.newSequenceOfTree();
        TrieNode rootNode = currentNode.disassemble(children);

        /*
         * Find lexicographical position or existing node
         */
        int insertPos = 0;
        Tree<TrieNode> childNode = null;
        for (int i = 0; i < children.length(); i++) {
            Tree<TrieNode> child = children.entry(i);
            if (child.root().nodeChar == insertChar) {
                childNode = child;
                break;
            } else if (child.root().nodeChar > insertChar) {
                insertPos = i;
                break;
            }
            insertPos = i + 1;
        }

        /*
         * If no matching character node exists, create and insert a new one
         */
        if (childNode == null) {
            childNode = new Tree1<>();
            Sequence<Tree<TrieNode>> emptyChildren = childNode
                    .newSequenceOfTree();
            childNode.assemble(new TrieNode(insertChar), emptyChildren); // Assemble with character node
            children.add(insertPos, childNode); // Insert in lexicographical order
        }

        currentNode.assemble(rootNode, children);
        return (Tree1) childNode;
    }

    /*
     * Search Structure; Search Helper Methods ---------------------------------
     */

    /**
     * Finds the node in the trie that corresponds to a given prefix.
     *
     * @param prefix
     *            the prefix to search for
     * @requires this.searchStructure != null
     * @return The node in the trie that corresponds to the prefix, or null if
     *         the prefix is not found
     * @ensures The returned node represents the endpoint of the path for
     *          {@code prefix} in the trie, if it exists
     */
    private Tree<TrieNode> findNodeForPrefix(String prefix) {
        /*
         * Start at the beggining of the tree
         */
        Tree<TrieNode> currentNode = this.searchStructure;

        /*
         * Iterate through the children and get the child node that matches with
         * the prefix
         */
        for (char prefixChar : prefix.toCharArray()) {
            currentNode = this.getChildNode(currentNode, prefixChar);
            if (currentNode == null) {
                return null;
            }
        }
        return currentNode;
    }

    /**
     * Collects all words from the trie that contain the specified substring.
     *
     * @param node
     *            the current node in the trie being traversed
     * @param substring
     *            the substring to search for within the words in the trie
     * @param prefix
     *            a StringBuilder used to construct the current word from the
     *            root to the current node
     * @param results
     *            a list that accumulates the words that contain the substring
     * @requires node != null && substring != null && results != null
     * @ensures The results list contains all words from the trie that include
     *          the specified substring, if any are found, in lexographical
     *          order
     */
    private void collectWordsContainingSubstring(Tree<TrieNode> node,
            String substring, StringBuilder prefix, List<String> results) {
        TrieNode rootNode = node.root();

        /*
         * If this node marks the end of a word, check if it contains the
         * substring
         */
        if (rootNode.isEndOfWord && prefix.toString().contains(substring)) {
            results.add(prefix.toString());
        }

        Sequence<Tree<TrieNode>> children = node.newSequenceOfTree();
        node.disassemble(children);

        /*
         * Iterate through and recurssively search all the children for words
         * containing the substring
         */
        for (Tree<TrieNode> child : children) {
            char ch = child.root().nodeChar;
            this.collectWordsContainingSubstring(child, substring,
                    new StringBuilder(prefix).append(ch), results);
        }

        node.assemble(rootNode, children);
    }

    /**
     * Collects all words from a specified node downwards in the trie.
     *
     * @param collectNode
     *            the starting node
     * @param prefix
     *            the prefix of words collected so far
     * @param results
     *            the list to store results in
     * @requires node != null and results != null
     * @ensures {@code results} will contain all words from {@code node}
     *          downward in the trie prefixed by {@code prefix}
     */
    private void collectWordsFromNode(Tree<TrieNode> collectNode,
            StringBuilder prefix, List<String> results) {

        /*
         * Starting node and base case
         */
        TrieNode rootNode = collectNode.root();
        if (rootNode.isEndOfWord) {
            results.add(prefix.toString());
        }

        Sequence<Tree<TrieNode>> children = collectNode.newSequenceOfTree();
        collectNode.disassemble(children);

        /*
         * Iterate through the children and recurssivley collect the words for
         * each node that matches the prefix
         */
        for (Tree<TrieNode> child : children) {
            char ch = child.root().nodeChar;
            this.collectWordsFromNode(child,
                    new StringBuilder(prefix).append(ch), results);
        }

        collectNode.assemble(rootNode, children);
    }

    /**
     * Retrieves a specific character child node from the current node.
     *
     * @param curNode
     *            the node to retrieve the child from
     * @param childChar
     *            the character of the child node to retrieve
     * @requires curNode != null
     * @return The child node corresponding to {@code childChar}, or null if no
     *         such child exists
     * @ensures Returns the child node of {@code curNode} that matches
     *          {@code childChar}, if present
     */
    private Tree<TrieNode> getChildNode(Tree<TrieNode> curNode,
            char childChar) {

        /*
         * If the node is empty return null
         */
        if (curNode.size() == 0) {
            return null;
        }

        Tree<TrieNode> returnNode = new Tree1<>();
        Sequence<Tree<TrieNode>> children = curNode.newSequenceOfTree();
        TrieNode curTrieNode = curNode.disassemble(children);

        /*
         * Iterate through the children of the node and find the coresponding
         * child node
         */
        for (Tree<TrieNode> child : children) {
            if (child.root().nodeChar == childChar) {
                returnNode = child;
                break;
            }
        }

        curNode.assemble(curTrieNode, children);
        return returnNode;
    }

    /**
     * Finds the closest matching word in the trie by calculating the edit
     * distance between the target word and potential matches. The search
     * prioritizes matches that meet the closest distance criteria and have
     * isEndOfWord = true.
     *
     * @param currentNode
     *            the current node being traversed in the trie
     * @param target
     *            the target word to match
     * @param currentIndex
     *            the current index in the target word that is being compared
     * @param currentDistance
     *            the current edit distance accumulated from root to the current
     *            node
     * @param currentPath
     *            the accumulated path string in the trie from the root to
     *            current node
     * @param closestMatch
     *            the best match found so far that meets the closest criteria
     * @param closestDistance
     *            the edit distance of the closest match found so far
     * @requires currentNode != null and target != null
     * @return The closest matching word in the trie based on edit distance
     * @ensures The closest match is the word with minimum edit distance to
     *          {@code target}, or lexicographically first if there are multiple
     *          matches with the same distance
     */
    private String findClosestMatch(Tree<TrieNode> curNode, String target,
            int currentIndex, int currentDistance, StringBuilder currentPath,
            String closestMatch, int closestDistance) {

        /*
         * If currentNode is null, return the closestMatch found so far
         */
        if (curNode == null) {
            return closestMatch;
        }

        /*
         * Check if we are at an end-of-word node and calculate distance
         */
        if (curNode.root().isEndOfWord) {
            int distance = this.calculateEditDistance(currentPath.toString(),
                    target);

            if (distance < closestDistance) {
                closestDistance = distance;
                closestMatch = currentPath.toString();
            }
        }

        Sequence<Tree<TrieNode>> children = curNode.newSequenceOfTree();
        TrieNode rootNode = curNode.disassemble(children);

        /*
         * Traverse each child node to find the best match
         */
        for (Tree<TrieNode> child : children) {
            TrieNode childNode = child.root();
            char childChar = childNode.nodeChar;

            /*
             * Check if the current character in target matches the child
             * character if it does, add 1 to newDistance
             */
            int newDistance = currentDistance;
            if (currentIndex < target.length()
                    && target.charAt(currentIndex) == childChar) {
                newDistance++;
            }

            currentPath.append(childChar);

            // Recursive call to get the best match from this subtree
            String subtreeClosestMatch = this.findClosestMatch(child, target,
                    currentIndex + 1, newDistance, currentPath, closestMatch,
                    closestDistance);

            // Calculate distance of the closest match found in the subtree
            int subtreeDistance = this
                    .calculateEditDistance(subtreeClosestMatch, target);

            // Update the closest match if a better one is found in this subtree
            if (subtreeDistance < closestDistance) {
                closestDistance = subtreeDistance;
                closestMatch = subtreeClosestMatch;
            }

            // Backtrack
            currentPath.deleteCharAt(currentPath.length() - 1);
        }

        return closestMatch;

    }

    /**
     * Calculates the edit distance (Levenshtein distance) between two strings,
     * representing the minimum number of character insertions, deletions, or
     * substitutions required to transform the source string into the target
     * string.
     *
     * @param source
     *            the source string to compare
     * @param target
     *            the target string to compare against
     * @requires source != null and target != null
     * @return The minimum edit distance between {@code source} and
     *         {@code target}
     * @ensures Returns the minimum number of edit operations needed to
     *          transform {@code source} into {@code target}
     */
    private int calculateEditDistance(String source, String target) {
        /*
         * Initialize a 2D array to store the minimum edit distance between
         * substrings of 'source' and 'target'. editDistance[i][j] will
         * represent the edit distance between the first i characters of
         * 'source' and the first j characters of 'target'.
         */
        int[][] editDistance = new int[source.length() + 1][target.length()
                + 1];

        /*
         * Fill the first column: transforming the substring of 'source' up to i
         * into an empty string requires i deletions.
         */
        for (int i = 0; i <= source.length(); i++) {
            editDistance[i][0] = i;
        }

        /*
         * Fill the first row: transforming an empty string into the substring
         * of 'target' up to j requires j insertions.
         */
        for (int j = 0; j <= target.length(); j++) {
            editDistance[0][j] = j;
        }

        /*
         * Compute edit distances for each substring combination of 'source' and
         * 'target'.
         */
        for (int i = 1; i <= source.length(); i++) {
            for (int j = 1; j <= target.length(); j++) {

                /*
                 * Determine the cost of replacing the current character. If
                 * characters match, cost is 0; otherwise, the cost is 1
                 * (substitution).
                 */
                int cost = (source.charAt(i - 1) == target.charAt(j - 1)) ? 0
                        : 1;

                /*
                 * Update editDistance[i][j] as the minimum of three possible
                 * operations: 1. Deletion from 'source': editDistance[i-1][j] +
                 * 1 2. Insertion to 'source': editDistance[i][j-1] + 1 3.
                 * Replacement in 'source' (or no change if characters are the
                 * same): editDistance[i-1][j-1] + cost
                 */
                editDistance[i][j] = Math.min(
                        Math.min(editDistance[i - 1][j] + 1, // deletion
                                editDistance[i][j - 1] + 1), // insertion
                        editDistance[i - 1][j - 1] + cost // replacement or match
                );
            }
        }

        /*
         * The edit distance between the full strings 'source' and 'target' is
         * the value in the last cell, representing the minimum operations
         * required to transform 'source' into 'target'.
         */
        return editDistance[source.length()][target.length()];
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No argument Constructor.
     */
    public SimpleSearchEngine1L() {
        this.createNewRep();
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @SuppressWarnings("unchecked")
    @Override
    public final SimpleSearchEngine1L<T> newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(Object source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof SimpleSearchEngine1L<?> : ""
                + "Violation of: source is of dynamic type SimpleSearchEngine1L<?>";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case: source must be of dynamic type
         * SortingMachine1L<?>, and the ? must be T or the call would not have
         * compiled.
         */
        SimpleSearchEngine1L<T> localSource = (SimpleSearchEngine1L<T>) source;
        this.entries = localSource.entries;
        this.insertionMode = localSource.insertionMode;
        this.searchStructure = localSource.searchStructure;
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void add(String tag, T value) {
        assert this.insertionMode = true : "Violation of: source is in insertion mode";
        assert tag != null : "Violation of: tag is not null";
        assert value != null : "Violation of: value is not null";

        this.entries.add(tag, value);
    }

    @Override
    public final void remove(String tag) {
        assert this.insertionMode = true : "Violation of: source is in insertion mode";
        assert tag != null : "Violation of: tag is not null";
        assert this.entries.size() > 0 : "Violation of: this.entries /= {}";

        this.entries.remove(tag);

    }

    @Override
    public final boolean contains(String tag) {
        assert tag != null : "Violation of: tag is not null";

        return this.entries.hasKey(tag);
    }

    @Override
    public final void changeToSearchMode() {
        assert this.insertionMode = true : "Violation of: source is in insertion mode";

        this.insertionMode = false;
        this.searchStructure = this.createTrie(this.entries);
    }

    @Override
    public final boolean isInInsertionMode() {

        return this.insertionMode;
    }

    @Override
    public final int size() {

        return this.entries.size();
    }

    @Override
    public final List<String> prefixSearch(String prefix) {
        assert this.insertionMode = false : "Violation of: source is not in insertion mode";

        List<String> results = new ArrayList<>();
        Tree<TrieNode> node = this.findNodeForPrefix(prefix);

        if (node != null) {
            this.collectWordsFromNode(node, new StringBuilder(prefix), results);
        }
        return results;
    }

    @Override
    public List<String> containsSearch(String substring) {
        assert this.insertionMode = false : "Violation of: source is not in insertion mode";

        List<String> results = new ArrayList<>();

        this.collectWordsContainingSubstring(this.searchStructure, substring,
                new StringBuilder(), results);

        return results;
    }

    @Override
    public String relativeSearch(String relativeTag) {
        assert this.insertionMode = false : "Violation of: source is not in insertion mode";

        return this.findClosestMatch(this.searchStructure, relativeTag, 0, 0,
                new StringBuilder(), null, Integer.MAX_VALUE);
    }

    public void printTrie(Tree<TrieNode> Trie) {
        StringBuilder currentWord = new StringBuilder();
        this.printTrie(Trie, currentWord, 0); // Start depth at 0
    }

    private void printTrie(Tree<TrieNode> node, StringBuilder currentWord,
            int depth) {
        // Create an indentation based on the current depth
        String indent = " ".repeat(depth * 2); // 2 spaces per depth level

        System.out.println(indent + "Character: " + node.root().nodeChar
                + ", Is End of Word: " + node.root().isEndOfWord);

        Sequence<Tree<TrieNode>> children = new Sequence3<>();
        node.disassemble(children);

        for (int i = 0; i < children.length(); i++) {
            Tree<TrieNode> child = children.entry(i);
            currentWord.append(child.root().nodeChar);
            // Increase depth by 1 for child nodes
            this.printTrie(child, currentWord, depth + 1);
            currentWord.setLength(currentWord.length() - 1); // Backtrack
        }
    }

    public static void main(String[] args) {
        // Create an instance of SimpleSearchEngine1L
        SimpleSearchEngine1L<String> sse = new SimpleSearchEngine1L<>();

        // Example entries to build the trie
        Map<String, String> entries = new Map4<>();
        entries.add("bat", "1");
        entries.add("base", "2");
        entries.add("ball", "3");
        entries.add("bomba", "4");

        // Create the trie using the example entries
        Tree<TrieNode> Trie = sse.createTrie(entries);

        // Print the trie
        System.out.println("Printing the trie:");
        sse.printTrie(Trie);
    }

}
