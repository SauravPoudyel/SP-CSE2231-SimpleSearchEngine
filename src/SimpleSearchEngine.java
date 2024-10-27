import components.map.Map.Pair;
import components.set.Set;

/**
 * {@code SimpleSearchEngineKernel<T>} enhanced with secondary methods.
 *
 * @param <T>
 *            type of {@code SimpleSearchEngine} entries
 *
 * @author Saurav Poudyel
 *
 */
public interface SimpleSearchEngine<T> extends SimpleSearchEngineKernel<T> {

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
    Pair<String, String> relativeSearch(String tag);

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
    Set<Pair<String, String>> prefixSearch(String prefix);

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
    Set<Pair<String, String>> containsSearch(String subString);
}
