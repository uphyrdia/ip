public class Task {
    protected String description;
    protected boolean done;

    public Task(String description) {
        this.description = description;
        this.done = false;
    }

    public String getStatusIcon() {
        return (done ? "X" : " ");
    }

    public void mark() {
        this.done = true;
    }

    public void unmark() {
        this.done = false;
    }

    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }

}
