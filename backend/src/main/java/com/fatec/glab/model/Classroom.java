package com.fatec.glab.model;

import java.util.List;

import com.fatec.glab.dto.classroom.ClassroomRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
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
