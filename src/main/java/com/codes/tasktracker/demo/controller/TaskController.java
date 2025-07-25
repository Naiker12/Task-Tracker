package com.codes.tasktracker.demo.controller;

import com.codes.tasktracker.demo.model.Task;
import com.codes.tasktracker.demo.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
}
