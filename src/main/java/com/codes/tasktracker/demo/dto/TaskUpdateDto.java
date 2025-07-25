package com.codes.tasktracker.demo.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.Optional;

public record TaskUpdateDto(
        @NotBlank String description,
        Boolean completed
) { }
