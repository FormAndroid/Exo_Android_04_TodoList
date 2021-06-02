package be.bxl.formation.exo_04_sqlite.models;

import java.time.LocalDate;
import java.util.Locale;

import be.bxl.formation.exo_04_sqlite.enums.PriorityEnum;

public class TodoTask {

    private long todoTaskId;
    private String name;
    private PriorityEnum priority;
    private LocalDate creationDate;
    private LocalDate limitDate;
    private LocalDate finishDate;

    public TodoTask(long todoTaskId, String name, PriorityEnum priority, LocalDate creationDate, LocalDate limitDate, LocalDate finishDate) {
        this(name, priority, creationDate, limitDate);
        this.todoTaskId = todoTaskId;
        this.finishDate = finishDate;
    }

    public TodoTask(String name, PriorityEnum priority, LocalDate creationDate, LocalDate limitDate) {
        this.todoTaskId = 0;
        this.name = name;
        this.priority = priority;
        this.creationDate = creationDate;
        this.limitDate = limitDate;
        this.finishDate = null;
    }


    public long getTodoTaskId() {
        return todoTaskId;
    }

    public void setTodoTaskId(long todoTaskId) {
        this.todoTaskId = todoTaskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PriorityEnum getPriority() {
        return priority;
    }

    public void setPriority(PriorityEnum priority) {
        this.priority = priority;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(LocalDate limitDate) {
        this.limitDate = limitDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public boolean isDone() {
        return this.finishDate != null;
    }
}
