import java.util.Scanner;

public class Phrolova {

    public static final int MAX_NUM_OF_TASKS = 100;

    public static void main(String[] args) {

        UI ui = new UI();
        Scanner in = new Scanner(System.in);
        TaskList tasks = new TaskList();

        ui.greet();
        String message;

        while (true) {
            message = in.nextLine();
            if (message.equals("bye")) {
                ui.print("Bye.");
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
            ui.print("Invalid command.");
        }

    }

}
