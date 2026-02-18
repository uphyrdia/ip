package phrolova;

import java.util.Scanner;
import phrolova.exception.*;
import phrolova.task.TaskList;
import phrolova.ui.UI;

public class Phrolova {

    public static void main(String[] args) {

        UI ui = new UI();
        Scanner in = new Scanner(System.in);
        TaskList tasks = new TaskList();

        ui.greet();

        String message;

        while (true) {
            message = in.nextLine();
            try {
                if (message.equals("bye")) {
                    ui.bye();
                    return;
                }
                if (message.equals("list")) {
                    tasks.list();
                    continue;
                }
                if (message.startsWith("mark")) {
                    tasks.mark(message);
                    continue;
                }
                if (message.startsWith("unmark")) {
                    tasks.unmark(message);
                    continue;
                }
                if (message.startsWith("delete")) {
                    tasks.delete(message);
                    continue;
                }
                if (message.startsWith("todo")) {
                    tasks.addTodo(message);
                    continue;
                }
                if (message.startsWith("deadline")) {
                    tasks.addDeadline(message);
                    continue;
                }
                if (message.startsWith("event")) {
                    tasks.addEvent(message);
                    continue;
                }
                throw new InvalidCommandException();
            } catch (InvalidCommandException e) {
                ui.print("Invalid command.");
            } catch (MissingTaskException e) {
                ui.print("Missing task.");
            }
        }

    }

}
