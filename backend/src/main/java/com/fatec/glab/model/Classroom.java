<<<<<<< HEAD:backend/src/main/java/com/fatec/glab/model/Classroom.java
package com.fatec.glab.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

<<<<<<< HEAD
<<<<<<< HEAD:backend/src/main/java/com/fatec/glab/model/Classroom.java
@Document(collection = "classrooms")
public class Classroom {
=======
=======
<<<<<<< HEAD:backend/src/main/java/com/fatec/glab/model/Laboratory.java
>>>>>>> e0318b6 (refactor: rename Laboratory>Classroom + new port)
import java.util.List;

@Document(collection = "laboratories")
public class Laboratory {
<<<<<<< HEAD
>>>>>>> 6599d0f (feat: create a software document and change laboratory):backend/src/main/java/com/fatec/glab/model/Laboratory.java
=======
=======
@Document(collection = "classrooms")
public class Classroom {
>>>>>>> b0e2203 (refactor: rename Laboratory>Classroom + new port):backend/src/main/java/com/fatec/glab/model/Classroom.java
>>>>>>> e0318b6 (refactor: rename Laboratory>Classroom + new port)

    @Id
    private String id;
    private String name;
    private int capacity;
    private List<Equipment> equipments;
    private List<Software> softwares;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    public List<Software> getSoftwares() {
        return softwares;
    }

    public void setSoftwares(List<Software> softwares) {
        this.softwares = softwares;
    }
}
=======
>>>>>>> 84569e2 (feat: create a software document and change laboratory):backend/src/main/java/com/fatec/glab/model/Laboratory.java
