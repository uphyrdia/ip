package phrolova.task;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.nio.file.Paths;
import java.util.List;

import phrolova.exception.*;
import phrolova.ui.UI;

public class TaskList {

    private final ArrayList<Task> tasks = new ArrayList<>();
    private final Path savePath = Paths.get("data","Phrolova.txt");
    UI ui = new UI();

    public void save() throws IOException {
        Files.createDirectories(savePath.getParent());
        List<String> lines = new ArrayList<>();
        for (Task t : tasks) {
            lines.add(t.toText());
        }
        Files.write(savePath, lines);
    }

    public void load() throws IOException {
        if (!Files.exists(savePath)) {
            return;
        }
        List<String> lines = Files.readAllLines(savePath);
        for (String line : lines) {
            try {
                tasks.add(parseTask(line));
            } catch (Exception e) {
                System.out.println("Warning: Skipping corrupted line -> " + line);
            }
        }
    }

    public Task parseTask(String line) {
        String[] parts = line.split("\\|");

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task = switch (type) {
            case "T" -> new Todo(description);
            case "D" -> new Deadline(description, parts[3]);
            case "E" -> new Event(description, parts[3], parts[4]);
            default -> throw new IllegalArgumentException("Invalid task type");
        };

        if (isDone) {
            task.markWithoutMessage();
        }

        return task;
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
        save();
    }

    public void mark(String message) throws IOException {
        try {
            int n = Integer.parseInt(message.replaceAll("\\D+", "")) - 1;
            tasks.get(n).mark();
        } catch (Exception e) {
            ui.print("Invalid command.");
        }
        save();
    }

    public void unmark(String message) throws IOException {
        try {
            int n = Integer.parseInt(message.replaceAll("\\D+", "")) - 1;
            tasks.get(n).unmark();
        } catch (Exception e) {
            ui.print("Invalid command.");
        }
        save();
    }

    public void addTodo(String message) throws MissingTaskException, IOException {
        if (message.length() < 5) {
            throw new MissingTaskException();
        }
        add(new Todo(message.substring(5)));
        save();
    }

    public void addDeadline(String message) throws IOException {
        int i = message.indexOf("/by");
        try {
            add(new Deadline(message.substring(9, i - 1), message.substring(i + 4)));
        } catch (Exception e) {
            ui.print("Invalid command.");
        }
        save();
    }

    public void addEvent(String message) throws IOException {
        int i = message.indexOf("/from");
        int j = message.indexOf("/to");
        try {
            add(new Event(message.substring(6, i - 1), message.substring(i + 6, j - 1), message.substring(j + 4)));
        } catch (Exception e) {
            ui.print("Invalid command.");
        }
        save();
    }

}
