import java.util.Scanner;

public class Phrolova {
    public static void main(String[] args) {
        // System.out.println("\tCiallo ～(∠・ω< )⌒★!");
        System.out.println("\tI'm Phrolova.\n\tWhat can I do for you?");
        String[] tasks = new String[100];
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
                    System.out.println("\t" + (j+1) + ". " + tasks[j]);
                }
                continue;
            }
            if (i == 100) {
                System.out.println("\tHow can you have so many things to do? Go relax.");
                return;
            }
            tasks[i] = message;
            System.out.println("\tadded: " + tasks[i]);
            i++;
        }
    }
}
