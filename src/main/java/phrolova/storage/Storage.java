package phrolova.storage;

import phrolova.task.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides persistence support for {@link Task} objects.
 *
 * <p>This class is responsible for serializing tasks to disk and
 * reconstructing them back into memory. Tasks are stored in a
 * plain-text file located at {@code data/Phrolova.txt}.</p>
 *
 * <p>Each task is stored in a pipe-delimited format:</p>
 *
 * <pre>
 * TYPE|DONE_FLAG|DESCRIPTION|[ADDITIONAL_FIELDS...]
 * </pre>
 *
 * <ul>
 *     <li>TYPE: {@code T} (Todo), {@code D} (Deadline), {@code E} (Event)</li>
 *     <li>DONE_FLAG: {@code true} (completed) or {@code false} (not completed)</li>
 *     <li>DESCRIPTION: task description</li>
 *     <li>ADDITIONAL_FIELDS: subtype-specific data (e.g., deadline date)</li>
 * </ul>
 *
 * <p>This class assumes that {@link Task#toText()} generates correctly
 * formatted lines compatible with {@link #parseTask(String)}.</p>
 */
public class Storage {

    /** Path to the save file on disk. */
    private final Path savePath = Paths.get("data","Phrolova.txt");

    /** In-memory task list bound to this storage instance. */
    private final ArrayList<Task> tasks;

    /**
     * Constructs a storage handler for the given task list.
     *
     * @param tasks the task list to persist and populate
     */
    public Storage(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Writes all tasks to the save file.
     *
     * <p>Creates parent directories if they do not already exist.
     * Existing file contents will be overwritten.</p>
     *
     * @throws IOException if directory creation or file writing fails
     */
    public void save() throws IOException {
        Files.createDirectories(savePath.getParent());
        List<String> lines = new ArrayList<>();
        for (Task t : tasks) {
            lines.add(t.toText());
        }
        Files.write(savePath, lines);
    }

    /**
     * Parses a serialized task line into a {@link Task} object.
     *
     * <p>Expected formats:</p>
     *
     * <pre>
     * T|0|description
     * D|1|description|deadline
     * E|0|description|from|to
     * </pre>
     *
     * @param line a single line from the save file
     * @return reconstructed {@link Task}
     */
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

    /**
     * Loads tasks from the save file into the in-memory task list.
     *
     * <p>If the save file does not exist, then an empty txt file is to be created.</p>
     *
     * <p>Malformed or corrupted lines are skipped. A warning message
     * is printed to standard output, and loading continues.</p>
     *
     * @throws IOException if the save file does not exist or cannot be read
     */
    public void load() throws IOException {
        if (!Files.exists(savePath)) {
            Files.createDirectories(savePath.getParent());
            Files.createFile(savePath);
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
