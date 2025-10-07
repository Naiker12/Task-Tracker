package com.codes.tasktracker.demo.service;

import com.codes.tasktracker.demo.exception.ResourceNotFoundException;
import com.codes.tasktracker.demo.model.Task;
import com.codes.tasktracker.demo.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Service for task-related operations.
 */
@Service
public final class TaskService {

    /**
     * The task repository.
     */
    private final TaskRepository taskRepository;

    /**
     * Constructs a new TaskService with the given repository.
     *
     * @param taskRepository The task repository.
     */
    public TaskService(final TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Creates a new task.
     *
     * @param description The description of the task.
     * @return The created task.
     */
    @Transactional
    public Task createTask(final String description) {
        Task task = new Task(description);
        return this.taskRepository.save(task);
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param id The ID of the task.
     * @return The task.
     */
    public Task getTask(final UUID id) {
        return this.taskRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Tarea no encontrada: " + id));
    }

    /**
     * Marks a task as completed.
     *
     * @param id The ID of the task to mark as completed.
     */
    @Transactional
    public void markTaskCompleted(final UUID id) {
        Task task = getTask(id);
        task.markCompleted();
        this.taskRepository.save(task);
    }

    /**
     * Lists all tasks.
     *
     * @return A list of all tasks.
     */
    public List<Task> listAllTasks() {
        return this.taskRepository.findAll();
    }

    /**
     * Updates a task.
     *
     * @param id          The ID of the task to update.
     * @param description The new description.
     * @param completed   The new completion status.
     * @return The updated task.
     */
    @Transactional
    public Task updateTask(
            final UUID id,
            final String description,
            final Boolean completed) {

        Task task = getTask(id);
        task.setDescription(description);
        if (completed != null && completed && !task.isCompleted()) {
            task.markCompleted();
        }
        return this.taskRepository.save(task);
    }

    /**
     * Deletes a task.
     *
     * @param id The ID of the task to delete.
     */
    @Transactional
    public void deleteTask(final UUID id) {
        Task task = this.taskRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Tarea no encontrada: " + id));
        this.taskRepository.delete(task);
    }
}
