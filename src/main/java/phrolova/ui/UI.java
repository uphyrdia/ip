package phrolova.ui;

import java.util.Scanner;

public class UI {

    Scanner in = new Scanner(System.in);

    public void greet() {
        print("I'm Phrolova.");
        print("What are you planning to do?");
    }

    public void bye() {
        print("Bye.");
    }

    public void print(String s) {
        System.out.println("\t" + s);
    }

    public String read() {
        return in.nextLine();
    }

}
