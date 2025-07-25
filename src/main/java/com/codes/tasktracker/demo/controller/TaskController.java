package com.codes.tasktracker.demo.controller;

import com.codes.tasktracker.demo.dto.TaskUpdateDto;
import com.codes.tasktracker.demo.model.Task;
import com.codes.tasktracker.demo.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Map<String, String> body) {
        String desc = body.get("description");
        if (desc == null || desc.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        Task task = service.createTask(desc);
        return ResponseEntity.created(URI.create("/tasks/" + task.getId())).body(task);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable UUID id) {
        Task task = service.getTask(id);
        return ResponseEntity.ok(task);
    }


    @GetMapping
    public ResponseEntity<List<Task>> listTasks() {
        List<Task> tasks = service.listAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable UUID id,
                                           @RequestBody TaskUpdateDto dto) {
        Task updated = service.updateTask(id, dto.description(), dto.completed());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        service.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}

