package com.fatec.glab.model;

import java.util.List;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "classrooms")
public class Classroom {

    @Id
    private String id;
    private String name;
    private Integer capacity;
    private List<Equipment> equipmentsId;
    private List<Software> softwaresId;
    public void setName(String name2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setName'");
    }
    public void setCapacity(Integer capacity2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setCapacity'");
    }
    public void setEquipments(List<Equipment> equipmentsId2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setEquipments'");
    }
    public void setSoftwares(List<Software> softwaresId2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setSoftwares'");
    }

}
