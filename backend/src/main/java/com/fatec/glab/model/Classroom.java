package com.fatec.glab.model;

import java.util.List;
import java.time.Instant;
import org.springframework.data.annotation.CreatedDate;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "classrooms")
public class Classroom {

    @Id
    private String id;
    private String name;
    private Integer capacity;
    private List<String> equipmentsId;
    private List<String> softwaresId;
    @CreatedDate
    private Instant createdDate;
}
