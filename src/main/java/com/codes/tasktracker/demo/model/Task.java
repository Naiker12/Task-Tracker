package com.codes.tasktracker.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

/**
 * Represents a task entity.
 */
@Entity
@Table(name = "tasks")
public final class Task {

    /**
     * The task's ID.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * The task's description.
     */
    @Column(nullable = false)
    private String description;

    /**
     * Whether the task is completed.
     */
    private boolean completed = false;

    /**
     * The timestamp of when the task was created.
     */
    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    /**
     * The timestamp of when the task was last updated.
     */
    private Instant updatedAt = Instant.now();

    /**
     * Default constructor for JPA.
     */
    protected Task() { }

    /**
     * Creates a new task with the given description.
     * @param description The task description.
     */
    public Task(final String description) {
        this.description = description;
    }

    /**
     * Returns the task's ID.
     * @return The ID.
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * Returns the task's description.
     * @return The description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Checks if the task is completed.
     * @return True if completed, false otherwise.
     */
    public boolean isCompleted() {
        return this.completed;
    }

    /**
     * Returns the creation timestamp.
     * @return The creation timestamp.
     */
    public Instant getCreatedAt() {
        return this.createdAt;
    }

    /**
     * Returns the last update timestamp.
     * @return The last update timestamp.
     */
    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    /**
     * Sets the task's description.
     * @param description The new description.
     */
    public void setDescription(final String description) {
        this.description = description;
        this.updatedAt = Instant.now();
    }

    /**
     * Marks the task as completed.
     */
    public void markCompleted() {
        this.completed = true;
        this.updatedAt = Instant.now();
    }
}