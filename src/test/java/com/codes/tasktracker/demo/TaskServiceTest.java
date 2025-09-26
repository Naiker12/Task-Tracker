package com.codes.tasktracker.demo;

import com.codes.tasktracker.demo.exception.ResourceNotFoundException; 
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest  {

    @Mock
    TaskRepository repository;

    @InjectMocks
    TaskService service;

    @Captor
    ArgumentCaptor<Task> taskCaptor;

    @Test
    void createTask_createsAndSavesWithDefaults() {
        when(repository.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));

        Task result = service.createTask("Primera tarea");

        verify(repository).save(taskCaptor.capture());
        Task saved = taskCaptor.getValue();

        assertThat(saved.getDescription()).isEqualTo("Primera tarea");
        assertThat(saved.isCompleted()).isFalse();
        assertThat(result.getDescription()).isEqualTo("Primera tarea");
        assertThat(result.isCompleted()).isFalse();
    }

    @Test
    void getTask_throwsWhenNotFound() {
        UUID fakeId = UUID.randomUUID();
        when(repository.findById(fakeId)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            service.getTask(fakeId);
        });

        assertThat(ex.getMessage()).contains("Tarea no encontrada");
        verify(repository).findById(fakeId);
    }

    @Test
    void markTaskCompleted_marksAndSaves() {
        UUID taskId = UUID.randomUUID();
        Task existing = new Task("Tarea pendiente"); // completed=false

        when(repository.findById(taskId)).thenReturn(Optional.of(existing));
        when(repository.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));

        service.markTaskCompleted(taskId);

        assertThat(existing.isCompleted()).isTrue();
        verify(repository).save(existing);
    }

    @Test
    void listAllTasks_returnsAllTasks() {
        List<Task> mockTasks = List.of(
                new Task("Tarea 1"),
                new Task("Tarea 2")
        );
        when(repository.findAll()).thenReturn(mockTasks);

        List<Task> result = service.listAllTasks();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(Task::getDescription)
                .containsExactly("Tarea 1", "Tarea 2");
        verify(repository).findAll();
    }

    @Test
    void updateTask_updatesExistingTask() {
        UUID id = UUID.randomUUID();
        Task existing = new Task("Vieja descripcion");

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));

        Task result = service.updateTask(id, "Nueva descripcion", true);

        assertThat(result.getDescription()).isEqualTo("Nueva descripcion");
        assertThat(result.isCompleted()).isTrue();
        verify(repository).save(existing);
    }

    @Test
    void updateTask_throwsWhenTaskNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            service.updateTask(id, "Nueva desc", true);
        });

        assertThat(ex.getMessage()).contains("Tarea no encontrada");
        verify(repository).findById(id);
    }

    @Test
    void deleteTask_deletesById() {
        UUID id = UUID.randomUUID();
        Task existing = new Task("Para eliminar");

        when(repository.findById(id)).thenReturn(Optional.of(existing));

        service.deleteTask(id);

        verify(repository).delete(existing);
    }

    @Test
    void deleteTask_throwsWhenTaskNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(opcional.empty());

        Exception ex = assertThrows(ResourceNotFoundException.class, () ->{
            Service.deleteTask(id);

        });

        assertThat(ex.getMessage()).contains("Tarea no encontrada");
        verify(repository).findById(id);
    }

    @Test
    void updateTask_doesNotMarkCompletedWhenFlagIsFalse() {
        UUID id = UUID.randomUUID();
        Task existing = new Task("Descripcion inicial");

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));

        Task result = service.updateTask(id, "Nueva descripcion", false);

        assertThat(result.getDescription()).isEqualTo("Nueva descripcion");
        assertThat(result.isCompleted()).isFalse();
        verify(repository).save(existing);
    }
}
