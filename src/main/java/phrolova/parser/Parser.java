package phrolova.parser;

import phrolova.exception.*;
import phrolova.ui.UI;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static phrolova.parser.Command.*;

public class Parser {

    private final UI ui = new UI();
    public int indexToMarkUnmarkDelete = 1;
    public String description = "";
    public String by = "";
    public String from = "";
    public String to = "";

    public int find(String[] words, String target) {
        return IntStream.range(0, words.length)
                .filter(i -> words[i].equals(target))
                .findFirst()
                .orElse(-1);
    }

    public Command parse(String message) throws MissingTaskException, MissingIndexException, MissingByException, MissingDeadlineException, MissingFromOrToException, FromToOrderException {

        String[] words = message.split("\\s+");

        if (words[0].equals("bye")) {
            return BYE;
        }

        if (words[0].equals("list")) {
            return LIST;
        }

        if (words[0].equals("mark")) {
            try {
                indexToMarkUnmarkDelete = Integer.parseInt(words[1]);
                return MARK;
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                throw new MissingIndexException();
            }
        }

        if (words[0].equals("unmark")) {
            try {
                indexToMarkUnmarkDelete = Integer.parseInt(words[1]);
                return UNMARK;
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                throw new MissingIndexException();
            }
        }

        if (words[0].equals("delete")) {
            try {
                indexToMarkUnmarkDelete = Integer.parseInt(words[1]);
                return DELETE;
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                throw new MissingIndexException();
            }
        }

        if (words[0].equals("todo")) {
            if (message.length() < 5) {
                throw new MissingTaskException();
            }
            this.description = message.substring(5);
            return TODO;
        }

        if (words[0].equals("deadline")) {
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

        if (words[0].equals("event")) {
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

        return INVALID;

    }

}
