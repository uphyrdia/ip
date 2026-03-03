package phrolova;

import java.io.IOException;
import phrolova.exception.*;
import phrolova.parser.Command;
import phrolova.parser.Parser;
import phrolova.task.TaskList;
import phrolova.ui.UI;

/**
 * Entry point and main application controller for Phrolova chatbot.
 *
 * <p>This class coordinates:
 * <ul>
 *     <li>User interaction via {@link UI}</li>
 *     <li>Command parsing via {@link Parser}</li>
 *     <li>Task management via {@link TaskList}</li>
 * </ul>
 *
 * <p>The application runs in a command loop until the {@code BYE}
 * command is received.</p>
 */
public class Phrolova {

    private final UI ui;
    private final TaskList tasks;
    private final Parser parser;

    /**
     * Constructs a new Phrolova application instance.
     *
     * <p>Initializes UI, task management & storage, and parser.</p>
     */
    public Phrolova() {
        ui = new UI();
        tasks = new TaskList();
        parser = new Parser();
    }

    /**
     * Application entry point.
     *
     * @param args command-line arguments (unused)
     * @throws IOException if a persistence operation fails
     */
    public static void main(String[] args) throws IOException {
        new Phrolova().run();
    }

    /**
     * Starts the main application loop.
     *
     * <p>Execution flow:
     * <ol>
     *     <li>Display greeting</li>
     *     <li>Load tasks from storage</li>
     *     <li>Continuously read and process user input</li>
     * </ol>
     *
     * <p>The loop terminates only when the {@code BYE} command is issued.</p>
     *
     * <p>Domain-specific parsing exceptions are caught and converted
     * into user-friendly messages.</p>
     *
     * @throws IOException if a task operation involving persistence (whenever
     * {@code tasks} is modified) fails
     */
    public void run () throws IOException {
        ui.greet();
        tasks.load();
        String message;
        while (true) {
            message = ui.read();
            try {
                Command cmd = parser.parse(message);
                switch (cmd) {
                    case BYE -> {
                        ui.bye();
                        return;
                    }
                    case LIST -> tasks.list();
                    case FIND -> tasks.find(parser.description);
                    case MARK -> tasks.mark(parser.index);
                    case UNMARK -> tasks.unmark(parser.index);
                    case DELETE -> tasks.delete(parser.index);
                    case TODO -> tasks.addTodo(parser.description);
                    case DEADLINE -> tasks.addDeadline(parser.description, parser.by);
                    case EVENT -> tasks.addEvent(parser.description, parser.from, parser.to);
                    default -> ui.print("Invalid command.");
                }
            } catch (MissingIndexException e) {
                ui.print("Pls enter the INDEX of the task. Find the index by list.");
            } catch (MissingTaskException e) {
                ui.print("Pls specify the task description.");
            } catch (MissingByException | MissingDeadlineException e) {
                ui.print("Pls specify due date.");
            } catch (MissingFromOrToException e) {
                ui.print("Pls specify the from and to date of the event.");
            } catch (FromToOrderException e) {
                ui.print("Pls respect the order of from to.");
            }
        }
    }

}
