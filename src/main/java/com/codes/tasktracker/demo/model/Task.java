package com.codes.tasktracker.demo.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String description;

    private boolean completed = false;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    private Instant updatedAt = Instant.now();

    // Constructores
    protected Task() { }

    public Task(String description) {
        this.description = description;
    }


    public UUID getId() { return id; }
    public String getDescription() { return description; }
    public boolean isCompleted() { return completed; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = Instant.now();
    }

    public void markCompleted() {
        this.completed = true;
        this.updatedAt = Instant.now();
    }
}
