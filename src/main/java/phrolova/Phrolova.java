package phrolova;

import java.io.IOException;
import phrolova.exception.*;
import phrolova.parser.Command;
import phrolova.parser.Parser;
import phrolova.task.TaskList;
import phrolova.ui.UI;

import static phrolova.parser.Command.*;

public class Phrolova {
    private final UI ui;
    private final TaskList tasks;
    private final Parser parser = new Parser();

    public Phrolova() {
        ui = new UI();
        tasks = new TaskList();
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
                Command command = parser.parse(message);
                if (command == BYE) {
                    ui.bye();
                    return;
                }
                if (command == LIST) {
                    tasks.list();
                    continue;
                }
                if (command == MARK) {
                    tasks.mark(parser.indexToMarkUnmarkDelete);
                    continue;
                }
                if (command == UNMARK) {
                    tasks.unmark(parser.indexToMarkUnmarkDelete);
                    continue;
                }
                if (command == DELETE) {
                    tasks.delete(parser.indexToMarkUnmarkDelete);
                    continue;
                }
                if (command == TODO) {
                    tasks.addTodo(parser.description);
                    continue;
                }
                if (command == DEADLINE) {
                    tasks.addDeadline(parser.description, parser.by);
                    continue;
                }
                if (command == EVENT) {
                    tasks.addEvent(parser.description, parser.from, parser.to);
                    continue;
                }
                if (command == INVALID) {
                    ui.print("Invalid command.");
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
