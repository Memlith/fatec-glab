package com.fatec.glab.dto.error;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponseDTO {

    LocalDateTime timestamp;
    String message;

    public ErrorResponseDTO(String message){
        this.timestamp = LocalDateTime.now();
        this.message = message;
    }

}
