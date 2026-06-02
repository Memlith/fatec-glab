package com.fatec.glab.model;

import java.time.Instant;
import org.springframework.data.annotation.CreatedDate;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "softwares")
public class Software {

    @Id
    private String id;
    private String name;
    @CreatedDate
    private Instant createdDate;
}
