package phrolova.task;

import java.io.IOException;
import java.util.ArrayList;

import phrolova.exception.*;
import phrolova.storage.Storage;
import phrolova.ui.UI;

public class TaskList {

    private final ArrayList<Task> tasks = new ArrayList<>();
    private final Storage storage = new Storage(tasks);
    private final UI ui = new UI();

    public void load() throws IOException {
        storage.load();
    }

    public void add(Task task) {
        tasks.add(task);
        ui.print("added: " + tasks.get(tasks.size()-1).toString());
    }

    public void list() {
        for (int i = 0; i < tasks.size(); i++) {
            ui.print((i + 1) + "." + tasks.get(i).toString());
        }
    }

    public void delete(String message) throws IOException {
        try {
            int n = Integer.parseInt(message.replaceAll("\\D+", "")) - 1;
            ui.print("Removed: " + tasks.get(n).toString());
            tasks.remove(n);
            ui.print("You have " + tasks.size() + " remaining task(s).");
        } catch (Exception e) {
            ui.print("Invalid command.");
        }
        storage.save();
    }

    public void mark(String message) throws IOException {
        try {
            int n = Integer.parseInt(message.replaceAll("\\D+", "")) - 1;
            tasks.get(n).mark();
        } catch (Exception e) {
            ui.print("Invalid command.");
        }
        storage.save();
    }

    public void unmark(String message) throws IOException {
        try {
            int n = Integer.parseInt(message.replaceAll("\\D+", "")) - 1;
            tasks.get(n).unmark();
        } catch (Exception e) {
            ui.print("Invalid command.");
        }
        storage.save();
    }

    public void addTodo(String message) throws MissingTaskException, IOException {
        if (message.length() < 5) {
            throw new MissingTaskException();
        }
        add(new Todo(message.substring(5)));
        storage.save();
    }

    public void addDeadline(String message) throws IOException {
        int i = message.indexOf("/by");
        try {
            add(new Deadline(message.substring(9, i - 1), message.substring(i + 4)));
        } catch (Exception e) {
            ui.print("Invalid command.");
        }
        storage.save();
    }

    public void addEvent(String message) throws IOException {
        int i = message.indexOf("/from");
        int j = message.indexOf("/to");
        try {
            add(new Event(message.substring(6, i - 1), message.substring(i + 6, j - 1), message.substring(j + 4)));
        } catch (Exception e) {
            ui.print("Invalid command.");
        }
        storage.save();
    }

}
