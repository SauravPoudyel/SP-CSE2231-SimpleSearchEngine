package components.simplesearchengine;

import java.util.List;

import components.map.Map.Pair;
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
     * @requires <pre> {@code tag} is in this.entries and this.entries /= {}
     * @updates this.entries
     * @returns {@code (tag, value)}
     * @ensures this.entries = #this.entries - {(tag, value)}
     */
    Pair<String, T> remove(String tag);

    /**
     * Removes any {@code (tag, value)} from the entries of {@code this}.
     *
     * @requires <pre> this.entries /= {}
     * @updates this.entries
     * @returns arbitrary {@code (tag, value)}
     * @ensures this.entries = #this.entries - {(tag, value)}
     */
    Pair<String, T> removeAny();

    /**
     * Returns the {@code value} corresponding to {@code tag} is in the entries
     * of {@code this}.
     *
     * @param tag
     *            the tag of the element to be searched for
     * @return the {@code value} associated with {@code tag}
     * @requires <pre> {@code tag} is in this.entries and this.entries /= {}
     * @ensures return value = {@code value} associated with {@code tag}
     */
    T valueOf(String tag);

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
     * @requires this.insertion_mode = true
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
     * Searches this.entries for {@code relTag} and returns the {@code tag}
     * value in {@code searchStructure} with the closest match
     *
     * @param relTag
     *            the relTag of the element to be searched for
     * @requires <pre> this.insertionMode == false
     * @return A tag that is within a set character distance within the
     *         {@code relTag} param
     * @ensures returned String is the closest match to {@code relTag} in
     *          this.entries
     */
    String relativeSearch(String relTag);

    /**
     * Searches this.entries for {@code tag} that have {@code prefix} and
     * returns a list of all the {@code tag}'s in this.entries with the entered
     * prefix
     *
     * @param prefix
     *            the prefix to be searched for
     * @requires <pre> this.insertionMode == false
     * @return A lexographically ordered list containing all tags in
     *         this.entries with the prefix {@code prefix}
     * @ensures The returned list, in lexographical order, that contains all
     *          {@code tag}'s' that have {@code prefix} in this.entries. AND
     *          prefix searching an empty string ("") returns a lexographical
     *          list of ALL {@code tag}'s in this.entries.
     */
    List<String> prefixSearch(String prefix);

    /**
     * Searches this.entries for {@code tag} that contains {@code subString} and
     * returns a list of all the {@code tag}'s in this.entries with the entered
     * substring
     *
     * @param substring
     *            the subString to be searched for
     * @requires <pre> this.insertionMode == false
     * @return A lexographically ordered list containing all tags in
     *         this.entries that contain {@code subString}
     * @ensures The returned list, in lexographical order, that contains all
     *          {@code tag}'s' that have {@code subString} in this.entries
     */
    List<String> containsSearch(String subString);

}
