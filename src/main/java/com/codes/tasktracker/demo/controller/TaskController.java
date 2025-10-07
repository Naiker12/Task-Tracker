package com.codes.tasktracker.demo.controller;

import com.codes.tasktracker.demo.dto.TaskUpdateDto;
import com.codes.tasktracker.demo.model.Task;
import com.codes.tasktracker.demo.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Controller for task-related operations.
 */
@RestController
@RequestMapping("/tasks")
public final class TaskController {

    /**
     * The task service.
     */
    private final TaskService taskService;

    /**
     * Constructs a new TaskController with the given service.
     *
     * @param taskService The task service.
     */
    public TaskController(final TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Creates a new task.
     *
     * @param body The request body containing the description.
     * @return The created task.
     */
    @PostMapping
    public ResponseEntity<Task> createTask(
            @RequestBody final Map<String, String> body) {

        String desc = body.get("description");
        if (desc == null || desc.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Task task = this.taskService.createTask(desc);
        return ResponseEntity
                .created(URI.create("/tasks/" + task.getId()))
                .body(task);
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param id The ID of the task.
     * @return The task.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable final UUID id) {
        Task task = this.taskService.getTask(id);
        return ResponseEntity.ok(task);
    }

    /**
     * Lists all tasks.
     *
     * @return A list of all tasks.
     */
    @GetMapping
    public ResponseEntity<List<Task>> listTasks() {
        List<Task> tasks = this.taskService.listAllTasks();
        return ResponseEntity.ok(tasks);
    }

    /**
     * Updates a task.
     *
     * @param id  The ID of the task to update.
     * @param dto The data transfer object with new values.
     * @return The updated task.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable final UUID id,
            @RequestBody final TaskUpdateDto dto) {

        Task updated = this.taskService
                .updateTask(id, dto.description(), dto.completed());
        return ResponseEntity.ok(updated);
    }

    /**
     * Deletes a task.
     *
     * @param id The ID of the task to delete.
     * @return A no-content response.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable final UUID id) {
        this.taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
