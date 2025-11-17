package com.fatec.glab.dto.professor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ProfessorRequestDTO(

        @NotBlank
        String name,
        @Email
        String email

){
}
