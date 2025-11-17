package com.fatec.glab.dto.error;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class errorResponseDTO {

    LocalDateTime timestamp;
    String message;

    public errorResponseDTO(String message){
        this.timestamp = LocalDateTime.now();
        this.message = message;
    }

}
