import java.util.Scanner;

public class Phrolova {

    public static void main(String[] args) {
        System.out.println("\tI'm Phrolova.\n\tWhat are you planning to do?");
        Task[] tasks = new Task[100];
        Scanner in = new Scanner(System.in);
        String message;
        int taskCount = 0;
        while (true) {
            message = in.nextLine();
            if (message.equals("bye")) {
                System.out.println("\tBye.");
                return;
            }
            if (message.equals("list")) {
                for (int j = 0; j < taskCount; j++) {
                    System.out.println("\t" + (j+1) + "." + tasks[j].toString());
                }
                continue;
            }
            if (message.startsWith("mark")) {
                int n = Integer.parseInt(message.replaceAll("\\D+", "")) - 1;
                if (n >= taskCount) {
                    System.out.println("\tThere are only " + taskCount + " tasks in the list.");
                    continue;
                }
                tasks[n].mark();
                System.out.println("\tMarked as done: " + tasks[n].toString());
                continue;
            }
            if (message.startsWith("unmark")) {
                int n = Integer.parseInt(message.replaceAll("\\D+", "")) - 1;
                if (n >= taskCount) {
                    System.out.println("\tThere are only " + taskCount + " tasks in the list.");
                    continue;
                }
                tasks[n].unmark();
                System.out.println("\tUnmarked: " + tasks[n].toString());
                continue;
            }
            if (taskCount == 100) {
                System.out.println("\tHow can you have so many things to do? Go relax.");
                return;
            }
            if (message.startsWith("todo")) {
                tasks[taskCount] = new Todo(message.substring(5));
            } else if (message.startsWith("deadline")) {
                int i = message.indexOf("/by");
                if (i == -1) {
                    System.out.println("\t/by when?");
                    continue;
                }
                tasks[taskCount] = new Deadline(message.substring(9, i-1), message.substring(i+4));
            } else if (message.startsWith("event")) {
                int i = message.indexOf("/from");
                if (i == -1) {
                    System.out.println("\t/from when?");
                    continue;
                }
                int j = message.indexOf("/to");
                if (j == -1) {
                    System.out.println("\t/to when?");
                    continue;
                }
                tasks[taskCount] = new Event(message.substring(6, i-1), message.substring(i+6, j-1), message.substring(j+4));
            } else {
                System.out.println("\tInvalid command.");
                continue;
            }
            System.out.println("\tadded: " + tasks[taskCount].toString());
            taskCount++;
        }
    }

}
