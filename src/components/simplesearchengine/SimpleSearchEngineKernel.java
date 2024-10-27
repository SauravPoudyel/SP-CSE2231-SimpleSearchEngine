package components.simplesearchengine;

import java.util.List;

import components.standard.Standard;

/**
 * Simple Search Engine Kernel component with primary methods.
 *
 * @param <T>
 *            type of {@code SimpleSearchEngineKernel} entries
 *
 * @mathsubtypes <pre>
 * SIMPLE_SEARCH_ENGINE_MODEL is (
 *   insertion_mode: boolean,
 *   entries: finite map of type (String, T),
 *   searchStructure: structure containing type (String) of how the tags are
 *      stored when changed to search mode
 *  )
 * </pre>
 * @mathmodel type SimpleSearchEngineKernel<T> is modeled by
 *            SIMPLE_SEARCH_ENGINE_MODEL
 *
 * @author Saurav Poudyel
 *
 */
public interface SimpleSearchEngineKernel<T> extends Standard {

    /**
     * Adds {@code (tag, value)} to the entries of {@code this}.
     *
     * @param tag
     *            the tag of the element to be added
     * @param value
     *            the content of the element to be added
     * @updates this.entries
     * @ensures this.entries = #this.entries union {(tag, value)}
     */
    void add(String tag, T value);

    /**
     * Removes {@code (tag, value)} from the entries of {@code this}.
     *
     * @param tag
     *            the tag of the element to be removed
     * @requires <pre> {tag} is in this.entries and this.entries /= {}
     * @updates this.entries
     * @ensures this.entries = #this.entries - {(tag, value)}
     */
    void remove(String tag);

    /**
     * Returns true iff {@code tag} is in the entries of {@code this}.
     *
     * @param tag
     *            the tag of the element to be searched for
     * @return true iff {@code tag} is in {@code this}
     * @ensures return value = if{@code this} is in this.entries
     */
    boolean contains(String tag);

    /**
     * Changes the mode of {@code this} from insertion to search.
     *
     * @updates this.insertion_mode
     * @requires this.insertion_mode
     * @ensures not this.insertion_mode
     */
    void changeToSearchMode();

    /**
     * Reports whether {@code this} is in insertion mode.
     *
     * @return true iff {@code this} is in insertion mode
     * @ensures isInInsertionMode = this.insertion_mode
     */
    boolean isInInsertionMode();

    /**
     * Reports the number of entries in {@code this}.
     *
     * @return the (multiset) size of {@code this.entries}
     * @ensures size = |this.entries|
     */
    int size();

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
    String relativeSearch(String tag);

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
    List<String> prefixSearch(String prefix);

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
    List<String> containsSearch(String subString);

}
