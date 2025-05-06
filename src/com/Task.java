package com;

import java.sql.Date;

public class Task {
    private String title;
    private String description;
    private String status;
    private Date dueDate;
    private boolean hasBeenAdded;
    public Task(String title, String description, String status, Date dueDate) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.hasBeenAdded = false;
    }

    // Getters & Setters
    public void setAdded() {this.hasBeenAdded = true;}
    public boolean getAdded() {return this.hasBeenAdded;}
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public Date getDueDate() { return dueDate; }
}

