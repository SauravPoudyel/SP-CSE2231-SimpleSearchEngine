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

}
