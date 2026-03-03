package phrolova.parser;

import phrolova.exception.*;
import phrolova.ui.UI;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static phrolova.parser.Command.*;

/**
 * Parses raw user input into {@link Command} values and extracts
 * associated arguments required for command execution.
 *
 * <p>This class performs lexical splitting of input strings and
 * validates command structure. Parsed arguments are stored in
 * instance fields for later retrieval by the caller.</p>
 *
 * <p>The parser assumes whitespace-separated tokens and specific
 * command markers such as {@code /by}, {@code /from}, and {@code /to}.</p>
 *
 * <p>This class is stateful: after a successful call to {@link #parse(String)},
 * the fields {@link #index}, {@link #description}, {@link #by},
 * {@link #from}, and {@link #to} may contain extracted arguments.</p>
 */
public class Parser {

    private final UI ui = new UI();

    /** 1-based task index used by commands such as mark, unmark, delete. */
    public int index = 1;

    /** Task description extracted from input. */
    public String description = "";

    /** Deadline date extracted from a deadline command. */
    public String by = "";

    /** Start date extracted from an event command. */
    public String from = "";

    /** End date extracted from an event command. */
    public String to = "";

    /**
     * Returns the index of the first occurrence of {@code target}
     * in the given array of words.
     *
     * @param words  tokenized input
     * @param target token to search for
     * @return zero-based index of {@code target}, or -1 if not found
     */
    public int find(String[] words, String target) {
        return IntStream.range(0, words.length)
                .filter(i -> words[i].equals(target))
                .findFirst()
                .orElse(-1);
    }

    /**
     * Parses a raw input message into a {@link Command}.
     *
     * <p>Depending on the command type, additional parameters are extracted
     * and stored in the corresponding fields of this parser instance.</p>
     *
     * <p>Recognized commands:
     * <ul>
     *     <li>{@code bye}</li>
     *     <li>{@code list}</li>
     *     <li>{@code find &lt;keyword&gt;}</li>
     *     <li>{@code mark &lt;index&gt;}</li>
     *     <li>{@code unmark &lt;index&gt;}</li>
     *     <li>{@code delete &lt;index&gt;}</li>
     *     <li>{@code todo &lt;description&gt;}</li>
     *     <li>{@code deadline &lt;description&gt; /by &lt;date&gt;}</li>
     *     <li>{@code event &lt;description&gt; /from &lt;start&gt; /to &lt;end&gt;}</li>
     * </ul>
     *
     * @param message raw user input
     * @return parsed {@link Command}, or {@code INVALID} if unrecognized
     *
     * @throws MissingTaskException        if a required task description is absent
     * @throws MissingIndexException       if an index is missing or invalid
     * @throws MissingByException          if {@code /by} is missing in a deadline command
     * @throws MissingDeadlineException    if deadline date is missing after {@code /by}
     * @throws MissingFromOrToException    if {@code /from} or {@code /to} is missing in an event command
     * @throws FromToOrderException        if {@code /from} appears after {@code /to}
     */
    public Command parse(String message) throws MissingTaskException, MissingIndexException, MissingByException, MissingDeadlineException, MissingFromOrToException, FromToOrderException {

        String[] words = message.split("\\s+");

        switch (words[0]) {
            case "bye" -> {
                return BYE;
            }
            case "list" -> {
                return LIST;
            }
            case "find" -> {
                description = message.substring(5);
                return FIND;
            }
            case "mark" -> {
                try {
                    index = Integer.parseInt(words[1]);
                    return MARK;
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    throw new MissingIndexException();
                }
            }
            case "unmark" -> {
                try {
                    index = Integer.parseInt(words[1]);
                    return UNMARK;
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    throw new MissingIndexException();
                }
            }
            case "delete" -> {
                try {
                    index = Integer.parseInt(words[1]);
                    return DELETE;
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    throw new MissingIndexException();
                }
            }
            case "todo" -> {
                if (message.length() < 5) {
                    throw new MissingTaskException();
                }
                this.description = message.substring(5);
                return TODO;
            }
            case "deadline" -> {
                int i = find(words, "/by");
                if (i == -1) {
                    throw new MissingByException();
                }
                if (i == 1) {
                    throw new MissingTaskException();
                }
                if (i == words.length - 1) {
                    throw new MissingDeadlineException();
                }
                int j = message.indexOf("/by");
                this.description = message.substring(9, j - 1);
                this.by = message.substring(j + 4);
                return DEADLINE;
            }
            case "event" -> {
                int i = find(words, "/from");
                int j = find(words, "/to");
                if (i == -1 || j == -1) {
                    throw new MissingFromOrToException();
                }
                if (i == 1) {
                    throw new MissingTaskException();
                }
                if (i > j) {
                    throw new FromToOrderException();
                }
                this.description = Arrays.stream(words, 1, i)
                        .collect(Collectors.joining(" "));
                this.from = Arrays.stream(words, i + 1, j)
                        .collect(Collectors.joining(" "));
                this.to = Arrays.stream(words, j + 1, words.length)
                        .collect(Collectors.joining(" "));
                return EVENT;
            }
            default -> {
                return INVALID;
            }
        }

    }

}
