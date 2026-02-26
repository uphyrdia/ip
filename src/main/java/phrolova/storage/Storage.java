package phrolova.storage;

import phrolova.task.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Storage {

    private final Path savePath = Paths.get("data","Phrolova.txt");
    private final ArrayList<Task> tasks;

    public Storage(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void save() throws IOException {
        Files.createDirectories(savePath.getParent());
        List<String> lines = new ArrayList<>();
        for (Task t : tasks) {
            lines.add(t.toText());
        }
        Files.write(savePath, lines);
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

    public void load() throws IOException {
        if (!Files.exists(savePath)) {
            throw new IOException();
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

}
