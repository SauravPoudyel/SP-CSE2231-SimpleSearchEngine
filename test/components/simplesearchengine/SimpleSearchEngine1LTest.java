package components.simplesearchengine;

/**
 * Customized JUnit test fixture for {@code SimpleSearchEngine1L}.
 */
public final class SimpleSearchEngine1LTest extends SimpleSearchEngineTest {

    @Override
    protected SimpleSearchEngine1L<String> constructorTest() {
        return new SimpleSearchEngine1L<String>();
    }

    @Override
    protected SimpleSearchEngine1L<String> constructorRef() {
        return new SimpleSearchEngine1L<String>();
    }

}
