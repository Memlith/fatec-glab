package com.fatec.glab.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserCreateRequestDTO(
        @NotBlank
        String name,
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
