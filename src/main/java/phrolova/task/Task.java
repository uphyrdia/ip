package phrolova.task;

import phrolova.ui.UI;

/**
 * Represents a generic task with a description and completion state.
 *
 * <p>This class serves as the base class for specific task types
 * such as {@link Todo}, {@link Deadline}, and {@link Event}.</p>
 *
 * <p>A task maintains:
 * <ul>
 *     <li>A textual description</li>
 *     <li>A completion flag</li>
 * </ul>
 *
 * <p>Subclasses may extend this class to include additional
 * metadata (e.g., deadlines or time intervals).</p>
 */
public class Task {

    protected String description;

    /** Completion status of the task. */
    protected boolean isDone;

    private final UI ui = new UI();

    /**
     * Constructs a task with the given description.
     * Completion status defaults to false, i.e., not done yet.
     *
     * @param description textual description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void mark() {
        this.isDone = true;
        ui.print("Marked as done: " + this.toString());
    }

    /**
     * Marks this task as completed without producing any output.
     *
     * <p>This method is intended for internal use during
     * deserialization from storage.</p>
     */
    public void markWithoutMessage() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
        ui.print("Unmarked: " + this.toString());
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * Returns a visual representation of the completion state.
     *
     * @return {@code "X"} if completed, otherwise a blank space
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Returns a user-facing string representation of this task.
     *
     * @return formatted string including status and description
     */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }

    /**
     * Serializes this task into a text format suitable for storage.
     *
     * <p>The returned string omits the task type prefix, which is to
     * be prepended by subclasses.</p>
     *
     * @return pipe-delimited string representation of the task state
     */
    public String toText() {
        return "|" + (isDone ? 1 : 0) + "|" + this.description;
    }

}
