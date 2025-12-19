
package com.example.dillydally;

public class Task {
    private String id;
    private String title;
    private boolean isCompleted;

    public Task() {
        // Default constructor
    }

    public Task(String id, String title) {
        this.id = id;
        this.title = title;
        this.isCompleted = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
