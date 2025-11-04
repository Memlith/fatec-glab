package com.fatec.glab.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

<<<<<<<< HEAD:backend/src/main/java/com/fatec/glab/model/Software.java
@Document(collection = "softwares")
public class Software {
========
import java.util.List;

@Document(collection = "laboratories")
public class Laboratory {
>>>>>>>> 6599d0f (feat: create a software document and change laboratory):backend/src/main/java/com/fatec/glab/model/Laboratory.java

    @Id
    private String id;
    private String name;
<<<<<<<< HEAD:backend/src/main/java/com/fatec/glab/model/Software.java
========
    private int capacity;
    private List<Equipment> equipments;
    private List<Software> softwares;
>>>>>>>> 6599d0f (feat: create a software document and change laboratory):backend/src/main/java/com/fatec/glab/model/Laboratory.java

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
<<<<<<<< HEAD:backend/src/main/java/com/fatec/glab/model/Software.java
========

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
>>>>>>>> 6599d0f (feat: create a software document and change laboratory):backend/src/main/java/com/fatec/glab/model/Laboratory.java
}
