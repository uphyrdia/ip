import java.util.Scanner;

public class Phrolova {
    public static void main(String[] args) {
        // System.out.println("\tCiallo ～(∠・ω< )⌒★!");
        System.out.println("\tI'm Phrolova.\n\tWhat are you planning to do?");
        Task[] tasks = new Task[100];
        Scanner in = new Scanner(System.in);
        String message;
        int i = 0;
        while (true) {
            message = in.nextLine();
            if (message.equals("bye")) {
                System.out.println("\tBye.");
                return;
            }
            if (message.equals("list")) {
                for (int j = 0; j < i; j++) {
                    System.out.println("\t" + (j+1) + "." + tasks[j].toString());
                }
                continue;
            }
            if (message.startsWith("mark")) {
                int n = Integer.parseInt(message.replaceAll("\\D+", "")) - 1;
                if (n >= i) {
                    System.out.println("\tThere are only " + i + " tasks in the list.");
                    continue;
                }
                tasks[n].mark();
                System.out.println("\tOkay.");
                System.out.println("\tMarked as done: " + tasks[n].toString());
                continue;
            }
            if (message.startsWith("unmark")) {
                int n = Integer.parseInt(message.replaceAll("\\D+", "")) - 1;
                if (n >= i) {
                    System.out.println("\tThere are only " + i + " tasks in the list.");
                    continue;
                }
                tasks[n].unmark();
                System.out.println("\tOkay.");
                System.out.println("\tUnmarked: " + tasks[n].toString());
                continue;
            }
            if (i == 100) {
                System.out.println("\tHow can you have so many things to do? Go relax.");
                return;
            }
            tasks[i] = new Task(message);
            System.out.println("\tadded: " + tasks[i].toString());
            // System.out.println("\tAnything else?");
            i++;
        }
    }
}
