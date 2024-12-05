package components.simplesearchengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import components.map.Map.Pair;

/**
 * {@code SimpleSearchEngineKernel<T>} enhanced with secondary methods.
 *
 * @param <T>
 *            type of {@code SimpleSearchEngine} entries
 *
 * @author Saurav Poudyel
 *
 */
public abstract class SimpleSearchEngineSecondary<T>
        implements SimpleSearchEngine<T> {
    /*
     * Public members ---------------------------------------------------------
     */

    /*
     * Common methods (from Object) -------------------------------------------
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("(");
        result.append(this.isInInsertionMode());
        result.append(", (");

        if (this.size() == 0) {
            result.append("))");
        } else {
            if (this.isInInsertionMode()) {
                /*
                 * Loop through and get all tags in the sse
                 */
                List<String> allTags = new ArrayList<>();
                List<Pair<String, T>> allPairs = new ArrayList<>();
                while (this.size() > 0) {
                    Pair<String, T> tempPair = this.removeAny();
                    allTags.add(tempPair.key());
                    allPairs.add(tempPair);
                }

                /*
                 * Add the pairs back to sse
                 */
                for (int i = 0; i < allPairs.size(); i++) {
                    this.add(allPairs.get(i).key(), allPairs.get(i).value());
                }

                /*
                 * Sort the tags by lexographical order (can't sort pairs)
                 */
                Collections.sort(allTags);

                /*
                 * Add the tags and tag values to the string
                 */
                for (int i = 0; i < allTags.size() - 1; i++) {
                    String tag = allTags.get(i);
                    result.append("(" + tag + ", " + this.valueOf(tag) + "), ");
                }
                /*
                 * Add last tag, value pair
                 */
                result.append("(" + allTags.get(allTags.size() - 1) + ", "
                        + this.valueOf(allTags.get(allTags.size() - 1)) + "))");

                allPairs.clear();
                allTags.clear();

            } else {
                /*
                 * Get all the tags in a list and loop through it
                 */
                List<String> allTags = this.prefixSearch("");
                for (int i = 0; i < allTags.size() - 1; i++) {
                    String tag = allTags.get(i);
                    result.append("(" + tag + ", " + this.valueOf(tag) + "), ");
                }
                /*
                 * Add last tag, value pair
                 */
                result.append("(" + allTags.get(allTags.size() - 1) + ", "
                        + this.valueOf(allTags.get(allTags.size() - 1)) + "))");

                allTags.clear();
            }
        }

        return result.toString();
    }

    @Override
    public boolean equals(Object refObject) {

        if (refObject == this) {
            return true;
        }
        if (refObject == null) {
            return false;
        }
        if (!(refObject instanceof SimpleSearchEngine1L<?>)) {
            return false;
        }

        /*
         * Cast and compare
         */
        SimpleSearchEngine<?> sse = (SimpleSearchEngine<?>) refObject;
        if (!(this.isInInsertionMode() == sse.isInInsertionMode())) {
            return false;
        }
        if (this.size() != sse.size()) {
            return false;
        }

        /*
         * Check tag equalities, we can do this because we know their both in
         * insertion mode or they're both not
         */
        if (this.isInInsertionMode()) {
            /*
             * Loop through and get all tags in the sse
             */
            List<Pair<String, T>> allPairs = new ArrayList<>();
            while (this.size() > 0) {
                allPairs.add(this.removeAny());
            }

            /*
             * Check if the refObject contains what it needs to
             */
            for (int i = 0; i < allPairs.size(); i++) {
                if (!(sse.contains(allPairs.get(i).key())) || !(allPairs.get(i)
                        .value().equals(sse.valueOf(allPairs.get(i).key())))) {
                    return false;
                }
            }

            /*
             * Add the pairs back to this
             */
            for (int i = 0; i < allPairs.size(); i++) {
                this.add(allPairs.get(i).key(), allPairs.get(i).value());
            }

            allPairs.clear();

        } else {
            /*
             * Get all the tags in a list and loop through it
             */
            List<String> allTags = this.prefixSearch("");
            for (int i = 0; i < allTags.size(); i++) {
                /*
                 * Compare each tag and it's value for equivalence, return false
                 * if they don't match
                 */
                if (!(sse.contains(allTags.get(i)))
                        || !(this.valueOf(allTags.get(i))
                                .equals(sse.valueOf(allTags.get(i))))) {
                    return false;
                }

            }
            allTags.clear();
        }

        return true;

    }
}
