package com.fatec.glab.model;

import java.util.List;

import com.fatec.glab.dto.classroom.ClassroomRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    public Classroom(ClassroomRequestDTO classroomDTO) {
        this.name = classroomDTO.name();
        this.capacity = classroomDTO.capacity();
        this.equipmentsId = classroomDTO.equipmentsId();
        this.softwaresId = classroomDTO.softwaresId();
    }
}
