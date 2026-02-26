package phrolova.task;

import phrolova.ui.UI;

public class Task {

    protected String description;
    protected boolean done;
    private final UI ui = new UI();

    public Task(String description) {
        this.description = description;
        this.done = false;
    }

    public void mark() {
        this.done = true;
        ui.print("Marked as done: " + this.toString());
    }

    public void markWithoutMessage() {
        this.done = true;
    }

    public void unmark() {
        this.done = false;
        ui.print("Unmarked: " + this.toString());
    }

    public String getStatusIcon() {
        return (done ? "X" : " ");
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }

    public String toText() {
        return "|" + (done ? 1 : 0) + "|" + this.description;
    }

}
