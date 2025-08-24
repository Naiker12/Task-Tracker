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

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        // Arrange: devolvemos el mismo objeto que recibe el repository
        when(repository.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));

        Task result = service.createTask("Primera tarea");

        // Assert: se guardó con la descripción y completed=false
        verify(repository).save(taskCaptor.capture());
        Task saved = taskCaptor.getValue();

        assertThat(saved.getDescription()).isEqualTo("Primera tarea");
        assertThat(saved.isCompleted()).isFalse();

        // El resultado que devuelve el servicio coincide
        assertThat(result.getDescription()).isEqualTo("Primera tarea");
        assertThat(result.isCompleted()).isFalse();
    }

    @Test
    void getTask_throwsWhenNotFound() {
        // Preparamos el repositorio para que devuelva vacío (simula que no existe la tarea)
        UUID fakeId = UUID.randomUUID();
        when(repository.findById(fakeId)).thenReturn(Optional.empty());

        // Aquí esperamos que el servicio lance una IllegalArgumentException
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            service.getTask(fakeId);
        });

        // Verificamos que el mensaje de la excepción contiene el texto esperado
        assertThat(ex.getMessage()).contains("Tarea no encontrada");

        // Verificamos que el repositorio fue llamado con el ID correcto
        verify(repository).findById(fakeId);
    }


    // --- Casos siguientes (los agregamos después de validar este) ---
    // @Test void markTaskCompleted_marksAndSaves() {}
    // @Test void updateTask_updatesDescriptionAndCompleted() {}
    // @Test void deleteTask_deletesExisting() {}
}
