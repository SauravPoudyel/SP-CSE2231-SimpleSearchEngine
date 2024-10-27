package components.simplesearchengine;

import components.map.Map.Pair;
import components.map.Map4;
import components.set.Set;
import components.sortingmachine.SortingMachine1L;
import components.tree.Tree1;

/**
 * {@code SimpleSearchEngine} represented as {@link components.map.Map4} and
 * suffix tree on {@link components.tree.Tree1} with implementations of primary
 * methods.
 *
 * @param <T>
 *            type of {@code SimpleSearchEngine} entries
 *
 * @mathsubtypes <pre>
 * SIMPLE_SEARCH_ENGINE_1L_MODEL is (
 *   insertion_mode: boolean,
 *   entries: finite map of type (String, T),
 *   searchStructure: suffix tree containing sorted letters of type String in entries (String, T)
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
    private Tree1<String> searchStructure;

    /**
     * Creator of initial representation.
     *
     * @param order
     *            total preorder for sorting
     */
    private void createNewRep() {
        this.insertionMode = true;
        this.entries = new Map4<String, T>();
        this.searchStructure = new Tree1<String>();
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
        SortingMachine1L<T> localSource = (SortingMachine1L<T>) source;
        this.insertionMode = localSource.insertionMode;
        this.entries = localSource.entries;
        this.searchStructure = localSource.searchStructure;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void add(String tag, T value) {
        assert this.insertionMode = true : "Violation of: source is in insertion mode";
        assert tag != null : "Violation of: tag is not null";
        assert value != null : "Violation of: value is not null";

    }

    /**
     * Removes {@code (tag, value)} from the entries of {@code this}.
     *
     * @param tag
     *            the tag of the element to be removed
     * @requires <pre> {tag} is in this.entries and this.entries /= {}
     * @updates this.entries
     * @ensures this.entries = #this.entries - {(tag, value)}
     */
    @Override
    public final void remove(String tag);

    /**
     * Returns true iff {@code tag} is in the entries of {@code this}.
     *
     * @param tag
     *            the tag of the element to be searched for
     * @return true iff {@code tag} is in {@code this}
     * @ensures return value = if{@code this} is in this.entries
     */
    @Override
    public final boolean contains(String tag) {

        return true;
    }

    /**
     * Changes the mode of {@code this} from insertion to search.
     *
     * @updates this.insertion_mode
     * @requires this.insertion_mode
     * @ensures not this.insertion_mode
     */
    @Override
    public final void changeToSearchMode() {

    }

    /**
     * Reports whether {@code this} is in insertion mode.
     *
     * @return true iff {@code this} is in insertion mode
     * @ensures isInInsertionMode = this.insertion_mode
     */
    @Override
    public final boolean isInInsertionMode() {

        return true;
    }

    /**
     * Reports the number of entries in {@code this}.
     *
     * @return the (multiset) size of {@code this.entries}
     * @ensures size = |this.entries|
     */
    @Override
    public final int size() {

        return 0;
    }

    /**
     * Searches this.entries for {@code tag} and returns the
     * {@code (tag, value)} of the closest match
     *
     * @param tag
     *            the tag of the element to be searched for
     * @requires <pre> this.entries \= {}
     * @return A tag pair that is within a set character distance within the
     *         suffix search tree of {@code tag}
     * @ensures String of Pair<String, T> is the closest match to {@code tag} in
     *          this.entries
     */
    @Override
    public final Pair<String, String> relativeSearch(String tag) {

        return new Pair<String, String>();
    }

    /**
     * Searches this.entries for {@code tag} that contains {@code prefix} and
     * returns a set of all the {@code (tag, url)} with the entered prefix
     *
     * @param prefix
     *            the prefix to be searched for
     * @requires <pre> this.entries \= {}
     * @return A set containing all tag pairs in this.entries with the prefix
     *         {@code prefix}
     * @ensures The returned set contains all Pair<String, T> that have
     *          {@code prefix} in the {@code tag}'s' of this.entries
     */
    @Override
    public final Map4<String, String> prefixSearch(String prefix){

        return new Map
    }

    /**
     * Searches this.entries for {@code tag} that contains {@code substring} and
     * returns a set of all the {@code (tag, value)} with the entered substring
     *
     * @param subString
     *            the substring to be searched for
     * @requires <pre> this.entries \= {}
     * @return A set containing all tag pairs in this.entries with the substring
     *         {@code substring}
     * @ensures The returned set contains all Pair<String, T> that have
     *          {@code subtring} in the {@code tag}'s' of this.entries
     */
    @Override
    public final Set<Pair<String, String>> containsSearch(String subString);

}
