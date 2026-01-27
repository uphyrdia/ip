import java.util.Scanner;

public class Phrolova {
    public static void main(String[] args) {
        // System.out.println("\tCiallo ～(∠・ω< )⌒★!");
        System.out.println("\tI'm Phrolova.\n\tWhat can I do for you?");
        Scanner in = new Scanner(System.in);
        String message = in.nextLine();;
        while (!message.equals("bye")) {
            System.out.println("\t" + message);
            message = in.nextLine();
        }
        System.out.println("\tBye.");
    }
}
