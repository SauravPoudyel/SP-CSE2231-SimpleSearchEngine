import java.util.Scanner;

import components.simplesearchengine.SimpleSearchEngine1L;

/**
 * A quick demo on class SimpleSearchEngine for searching class codes.
 *
 * @author Saurav Poudyel
 */
public class SimpleSearchEngineDemo2 {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        // Create a SimpleSearchEngine with predefined course codes and names
        SimpleSearchEngine1L<String> classRegister = createClassRegister();
        classRegister.changeToSearchMode();

        System.out.println("Welcome to the University Class Register!");

        String quit = "n";
        while (!quit.equals("y")) {
            System.out.println(
                    "\nEnter a class code to search (e.g., CSE2231): ");
            String classCode = scan.nextLine();
            System.out.println();

            if (classRegister.contains(classCode)) {
                System.out.println("Class found!");
                System.out.println("Class Code: " + classCode);
                System.out.println(
                        "Course Name: " + classRegister.valueOf(classCode));
            } else {
                String suggestion = classRegister.relativeSearch(classCode);
                System.out.println("Class code not found.");
                if (suggestion != null) {
                    System.out.println("Did you mean: " + suggestion + "?");
                }
            }

            System.out.println("\nDo you want to quit? (y = yes, n = no)");
            quit = scan.nextLine();
        }

        System.out.println("Goodbye!");
        scan.close();
    }

    /**
     * Creates a SimpleSearchEngine1L instance with a predefined class register.
     *
     * @return a SimpleSearchEngine1L instance with class codes and course names
     */
    private static SimpleSearchEngine1L<String> createClassRegister() {
        SimpleSearchEngine1L<String> sse = new SimpleSearchEngine1L<>();
        sse.add("CSE2231", "Software Development and Design");
        sse.add("CSE2321", "Foundations of Computer Science");
        sse.add("CSE2421", "Systems I: Introduction to Low-Level Programming");
        sse.add("CSE3430", "Introduction to Operating Systems");
        sse.add("CSE3241", "Introduction to Database Systems");
        sse.add("CSE5521",
                "Machine Learning and Statistical Pattern Recognition");
        return sse;
    }
}
