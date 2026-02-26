package phrolova;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import phrolova.exception.*;
import phrolova.parser.Command;
import phrolova.parser.Parser;
import phrolova.task.TaskList;
import phrolova.ui.UI;

public class Phrolova {

    private final UI ui;
    private final TaskList tasks;
    private final Parser parser;

    public Phrolova() {
        ui = new UI();
        tasks = new TaskList();
        parser = new Parser();
    }

    public static void main(String[] args) throws IOException {
        new Phrolova().run();
    }

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
