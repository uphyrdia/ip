# Phrolova User Guide


![](/docs/images/product_screenshot.png)

Phrolova is a lightweight Command Line Interface (CLI) task management chatbot. It helps you manage todos, deadlines, and events efficiently using typed commands.

If you prefer keyboard-driven workflows, Phrolova lets you manage your tasks quickly without navigating graphical menus.

---
## Quick Start
1. Ensure you have **Java 17 or above** installed on your computer.

2. Download the latest `phrolova.jar` file.

3. Copy the `.jar` file into a folder you want to use as the home directory.

4. Open a terminal and navigate (`cd`) into that folder.

5. Run the application:
`java -jar phrolova.jar`
6. You should see a greeting message from Phrolova.

7. Type commands in formats described below and press `Enter` to execute them.

### Notes about Command Format

- Words in `UPPER_CASE` are parameters supplied by the user.  
  Example: `todo DESCRIPTION`

- Parameters in `[square brackets]` are optional (if applicable).

- `INDEX` refers to the number shown in the `list` command output.

- Commands are case-sensitive.
---
## Features
### Listing All Tasks

Format :
`
list
`


Example:

---
### Adding a Task without any Date

Format :
`
todo DESCRIPTION
`

Example:

---
### Adding a Task with a Deadline

Format :
`
deadline DESCRIPTION /by DUE_DATE
`


Example:

---
### Adding a Task with Start & End Dates\

Format :
`
event DESCRIPTION /from START_DATE /to END_DATE
`

Example :

---
### Marking a Task as Done

Format :
`
mark INDEX
`

Example :

---
### Unmarking a Task

Format :
`
unmark INDEX
`

Example :

---

### Deleting a Task

Format :
`
delete INDEX
`

Example :

---

### Finding Tasks

Finds all tasks whose description contains the string `KEYWORD`

Format :
`
find KEYWORD
`

Example :

---

### Exiting the Program

Format :
`
bye
`

Example :

---

### Saving Data

Phrolova automatically saves tasks to disk after any command that modifies the task list.

There is **no** need to manually save.

### Editing the Data File

Tasks are stored locally in `/data/Phrolova.txt`

Advanced users may edit the file manually.

⚠ **Caution:**
- If the file format becomes invalid, the application may fail to load tasks.
- Always create a backup before manual edits.

---

## FAQ

### Q: How do I move my tasks to another computer?

Copy the data file `/data/Phrolova.txt` from the original machine and replace the data file in the new machine's `/data/` directory.
