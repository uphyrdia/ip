package phrolova.task;

import java.io.IOException;
import java.util.ArrayList;

import phrolova.exception.*;
import phrolova.storage.Storage;
import phrolova.ui.UI;

/**
 * Represents the in-memory collection of {@link Task} objects.
 *
 * <p>This class is responsible for:
 * <ul>
 *     <li>Maintaining the task list</li>
 *     <li>Coordinating persistence via {@link Storage}</li>
 *     <li>Delegating user-facing output to {@link UI}</li>
 * </ul>
 *
 * <p>All mutating operations automatically persist changes to storage.
 */
public class TaskList {

    private final ArrayList<Task> tasks = new ArrayList<>();
    private final Storage storage = new Storage(tasks);
    private final UI ui = new UI();

    /**
     * Loads persisted tasks from storage into memory.
     *
     * <p>If the backing data file does not exist, a message is printed
     * and execution continues with an empty task list.
     */
    public void load() {
        try {
            storage.load();
        } catch (IOException e) {
            ui.print("Data file does not exist, creating one.");
        }
    }

    /**
     * Persists the current task list to storage.
     *
     * @throws IOException if writing to the data file fails
     */
    public void save() throws IOException {
        storage.save();
    }

    /**
     * Adds a task to the list and prints confirmation to the user.
     *
     * @param task the {@link Task} to be added
     */
    public void add(Task task) {
        tasks.add(task);
        ui.print("added: " + tasks.get(tasks.size() - 1).toString());
    }

    /**
     * Prints all tasks in the list.
     */
    public void list() {
        for (int i = 0; i < tasks.size(); i++) {
            ui.print((i + 1) + "." + tasks.get(i).toString());
        }
    }

    /**
     * Removes a task by 1-based index.
     *
     * <p>If the index is invalid, an error message is printed.
     * After deletion, the updated task count is displayed.
     *
     * @param i 1-based (starting from 1 instead of 0) index of the task to remove
     * @throws IOException if saving to storage fails
     */
    public void delete(int i) throws IOException {
        try {
            ui.print("Removed: " + tasks.get(i - 1).toString());
            tasks.remove(i - 1);
        } catch (IndexOutOfBoundsException e) {
            ui.print("Index out of bounds.");
        }
        ui.print("You have " + tasks.size() + " remaining task(s).");
        save();
    }

    /**
     * Prints all tasks whose description contains the given keyword.
     *
     * <p>Matching is currently case-sensitive and based on substring containment.
     *
     * @param keyword the substring to search for within task descriptions
     */
    public void find(String keyword) {
        tasks.stream()
                .filter(t -> t.getDescription().contains(keyword))
                .forEach(t -> ui.print(t.toString()));
    }

    /**
     * Marks a task as completed using a 1-based index.
     *
     * <p>If the index is invalid, an error message is printed.
     *
     * @param i 1-based index of the task to mark
     * @throws IOException if saving to storage fails
     */
    public void mark(int i) throws IOException {
        try {
            tasks.get(i - 1).mark();
        } catch (IndexOutOfBoundsException e) {
            ui.print("Index out of bounds.");
        }
        save();
    }

    public void unmark(int i) throws IOException {
        try {
            tasks.get(i - 1).unmark();
        } catch (IndexOutOfBoundsException e) {
            ui.print("Index out of bounds.");
        }
        save();
    }

    /**
     * Creates and adds a {@link Todo} task.
     *
     * @param description task description
     * @throws IOException if saving to storage fails
     */
    public void addTodo(String description) throws IOException {
        add(new Todo(description));
        save();
    }

    /**
     * Creates and adds a {@link Deadline} task.
     *
     * @param description task description
     * @param dueDate the due date string of the task
     * @throws IOException if saving to storage fails
     */
    public void addDeadline(String description, String dueDate) throws IOException {
        add(new Deadline(description, dueDate));
        save();
    }

    /**
     * Creates and adds an {@link Event} task.
     *
     * @param description task description
     * @param fromDate start date/time string
     * @param toDate end date/time string
     * @throws IOException if saving to storage fails
     */
    public void addEvent(String description, String fromDate, String toDate) throws IOException {
        add(new Event(description, fromDate, toDate));
        save();
    }

}
