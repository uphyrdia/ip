package phrolova.ui;

import java.util.Scanner;

/**
 * Handles all user interaction for the application.
 *
 * <p>This class is responsible for:
 * <ul>
 *     <li>Displaying formatted output to the console</li>
 *     <li>Reading user input from standard input</li>
 * </ul>
 *
 * <p>All printed messages are prefixed with a tab character
 * for consistent console formatting.</p>
 */
public class UI {

    Scanner in = new Scanner(System.in);

    /**
     * Displays the application greeting message.
     */
    public void greet() {
        print("I'm Phrolova.");
        print("What are you planning to do?");
    }

    /**
     * Displays the application exiting message.
     */
    public void bye() {
        print("Bye.");
    }

    /**
     * Prints a message to standard output.
     *
     * <p>A tab character is prepended to maintain consistent formatting.</p>
     *
     * @param s the message to print
     */
    public void print(String s) {
        System.out.println("\t" + s);
    }

    /**
     * Reads a full line of input from the user.
     *
     * @return the input line as entered
     */
    public String read() {
        return in.nextLine();
    }

}
