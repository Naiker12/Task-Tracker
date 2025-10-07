package com.codes.tasktracker.demo.controller;

import com.codes.tasktracker.demo.dto.TaskUpdateDto;
import com.codes.tasktracker.demo.exception.ResourceNotFoundException;
import com.codes.tasktracker.demo.model.Task;
import com.codes.tasktracker.demo.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    @Test
    void createTaskReturns201WhenCreated() throws Exception {
        UUID id = UUID.randomUUID();
        Task task = new Task("Test Description");
        task.setId(id);
        when(taskService.createTask(anyString())).thenReturn(task);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"Test Description\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/tasks/" + task.getId()))
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    void createTaskReturns400WhenDescriptionIsBlank() throws Exception {
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getTaskReturns200WhenFound() throws Exception {
        UUID id = UUID.randomUUID();
        Task task = new Task("Existing Task");
        task.setId(id);
        when(taskService.getTask(id)).thenReturn(task);

        mockMvc.perform(get("/tasks/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.description").value("Existing Task"));
    }

    @Test
    void getTaskReturns404WhenNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        when(taskService.getTask(id)).thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/tasks/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void listTasksReturns200WithListOfTasks() throws Exception {
        Task task1 = new Task("Task 1");
        task1.setId(UUID.randomUUID());
        Task task2 = new Task("Task 2");
        task2.setId(UUID.randomUUID());

        when(taskService.listAllTasks()).thenReturn(List.of(task1, task2));

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].description").value("Task 1"));
    }

    @Test
    void updateTaskReturns200WhenUpdated() throws Exception {
        UUID id = UUID.randomUUID();
        TaskUpdateDto dto = new TaskUpdateDto("Updated description", true);
        Task updatedTask = new Task("Updated description");
        updatedTask.setId(id);
        updatedTask.markCompleted();

        when(taskService.updateTask(id, "Updated description", true)).thenReturn(updatedTask);

        mockMvc.perform(put("/tasks/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.description").value("Updated description"))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void deleteTaskReturns204WhenDeleted() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(taskService).deleteTask(id);

        mockMvc.perform(delete("/tasks/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTaskReturns404WhenNotFound() throws Exception {
        UUID id = UUID.randomUUID();
        doThrow(new ResourceNotFoundException("Not found")).when(taskService).deleteTask(id);

        mockMvc.perform(delete("/tasks/{id}", id))
                .andExpect(status().isNotFound());
    }
}
