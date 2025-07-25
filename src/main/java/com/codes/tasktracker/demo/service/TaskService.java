package com.codes.tasktracker.demo.service;

import com.codes.tasktracker.demo.exception.ResourceNotFoundException;
import com.codes.tasktracker.demo.model.Task;
import com.codes.tasktracker.demo.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Task createTask(String description) {
        Task task = new Task(description);
        return repository.save(task);
    }

    public Task getTask(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada: " + id));
    }

    // --- Método para ejemplo de marca completada ---
    @Transactional
    public void markTaskCompleted(UUID id) {
        Task task = getTask(id);
        task.markCompleted();
        repository.save(task);
    }

    // -- Metedo para lista tarea ---
    public List<Task> listAllTasks() {
        return repository.findAll();
    }


    // -- Metodo para actualizar --
    @Transactional
    public Task updateTask(UUID id, String description, Boolean completed) {
        Task task = getTask(id);
        task.setDescription(description);
        if (completed != null && completed && !task.isCompleted()) {
            task.markCompleted();
        }
        return repository.save(task);
    }

    // -- Metodo para eliminar
    @Transactional
    public void deleteTask(UUID id) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada: " + id));
        repository.delete(task);
    }



}
