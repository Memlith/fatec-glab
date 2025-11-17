package com.fatec.glab.dto.equipment;

import jakarta.validation.constraints.NotBlank;

public record EquipmentRequestDTO(
        @NotBlank
        String name
) {
}
