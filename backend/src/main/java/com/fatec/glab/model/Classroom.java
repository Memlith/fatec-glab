package com.fatec.glab.model;

import java.util.List;

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

}
