package com.codes.tasktracker.demo.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Data transfer object for updating a task.
 *
 * @param description The new description.
 * @param completed   The new completion status.
 */
public record TaskUpdateDto(
        @NotBlank String description,
        Boolean completed
) {
}
