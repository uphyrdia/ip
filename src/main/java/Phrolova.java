import java.util.Scanner;

public class Phrolova {

    public static final int MAX_NUM_OF_TASKS = 100;

    public static void main(String[] args) {
        UI ui = new UI();
        Scanner in = new Scanner(System.in);
        Task[] tasks = new Task[MAX_NUM_OF_TASKS];
        int taskCount = 0;

        ui.print("I'm Phrolova.");
        ui.print("What are you planning to do?");

        String message;

        while (true) {
            message = in.nextLine();
            if (message.equals("bye")) {
                ui.print("Bye.");
                return;
            }
            if (message.equals("list")) {
                for (int i = 0; i < taskCount; i++) {
                    ui.print((i+1) + "." + tasks[i].toString());
                }
                continue;
            }
            if (message.startsWith("mark")) {
                try {
                    int n = Integer.parseInt(message.replaceAll("\\D+", "")) - 1;
                    tasks[n].mark();
                    ui.print("Marked as done: " + tasks[n].toString());
                } catch (Exception e) {
                    ui.print("Invalid command.");
                }
                continue;
            }
            if (message.startsWith("unmark")) {
                try {
                    int n = Integer.parseInt(message.replaceAll("\\D+", "")) - 1;
                    tasks[n].unmark();
                    ui.print("Unmarked: " + tasks[n].toString());
                } catch (Exception e) {
                    ui.print("Invalid command.");
                }
                continue;
            }
            if (message.startsWith("todo")) {
                tasks[taskCount] = new Todo(message.substring(5));
                ui.print("added: " + tasks[taskCount].toString());
                taskCount++;
                continue;
            }
            if (message.startsWith("deadline")) {
                int i = message.indexOf("/by");
                try {
                    tasks[taskCount] = new Deadline(message.substring(9, i - 1), message.substring(i + 4));
                } catch (Exception e) {
                    ui.print("Invalid command.");
                }
                ui.print("added: " + tasks[taskCount].toString());
                taskCount++;
                continue;
            }
            if (message.startsWith("event")) {
                int i = message.indexOf("/from");
                int j = message.indexOf("/to");
                try {
                    tasks[taskCount] = new Event(message.substring(6, i - 1), message.substring(i + 6, j - 1), message.substring(j + 4));
                } catch (Exception e) {
                    ui.print("Invalid command.");
                }
                ui.print("added: " + tasks[taskCount].toString());
                taskCount++;
                continue;
            }
            ui.print("Invalid command.");
        }
    }

}
