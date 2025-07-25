package com.codes.tasktracker.demo.repository;

import com.codes.tasktracker.demo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> { }
