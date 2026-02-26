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
        ui.print("added: " + tasks.get(tasks.size() - 1).toString());
    }

    public void list() {
        for (int i = 0; i < tasks.size(); i++) {
            ui.print((i + 1) + "." + tasks.get(i).toString());
        }
    }

    public void delete(int i) throws IOException {
        try {
            ui.print("Removed: " + tasks.get(i - 1).toString());
            tasks.remove(i - 1);
        } catch (IndexOutOfBoundsException e) {
            ui.print("Index out of bounds.");
        }
        ui.print("You have " + tasks.size() + " remaining task(s).");
        storage.save();
    }

    public void mark(int i) throws IOException {
        try {
            tasks.get(i - 1).mark();
        } catch (IndexOutOfBoundsException e) {
            ui.print("Index out of bounds.");
        }
        storage.save();
    }

    public void unmark(int i) throws IOException {
        try {
            tasks.get(i - 1).unmark();
        } catch (IndexOutOfBoundsException e) {
            ui.print("Index out of bounds.");
        }
        storage.save();
    }

    public void addTodo(String description) throws IOException {
        add(new Todo(description));
        storage.save();
    }

    public void addDeadline(String description, String dueDate) throws IOException {
        add(new Deadline(description, dueDate));
        storage.save();
    }

    public void addEvent(String description, String fromDate, String toDate) throws IOException {
        add(new Event(description, fromDate, toDate));
        storage.save();
    }

}
