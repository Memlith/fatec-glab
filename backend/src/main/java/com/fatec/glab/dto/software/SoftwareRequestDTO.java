package com.fatec.glab.dto.software;

import jakarta.validation.constraints.NotBlank;

public record SoftwareRequestDTO(
        @NotBlank
        String name
) {
}
