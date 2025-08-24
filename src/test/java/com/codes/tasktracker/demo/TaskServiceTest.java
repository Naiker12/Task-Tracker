package com.codes.tasktracker.demo;

import com.codes.tasktracker.demo.model.Task;
import com.codes.tasktracker.demo.repository.TaskRepository;
import com.codes.tasktracker.demo.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    TaskRepository repository;

    @InjectMocks
    TaskService service;

    @Captor
    ArgumentCaptor<Task> taskCaptor;

    @Test
    void createTask_createsAndSavesWithDefaults() {
        // Arrange: devolvemos el mismo objeto que recibe el repo
        when(repository.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));

        // Act
        Task result = service.createTask("Primera tarea");

        // Assert: se guardó con la descripción y completed=false
        verify(repository).save(taskCaptor.capture());
        Task saved = taskCaptor.getValue();

        assertThat(saved.getDescription()).isEqualTo("Primera tarea");
        assertThat(saved.isCompleted()).isFalse();

        // el resultado que devuelve el servicio coincide
        assertThat(result.getDescription()).isEqualTo("Primera tarea");
        assertThat(result.isCompleted()).isFalse();
    }

    // --- Casos siguientes (los agregamos después de validar este) ---
    // @Test void getTask_throwsWhenNotFound() {}
    // @Test void markTaskCompleted_marksAndSaves() {}
    // @Test void updateTask_updatesDescriptionAndCompleted() {}
    // @Test void deleteTask_deletesExisting() {}
}
