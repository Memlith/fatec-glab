package com.fatec.glab.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "equipments")
public class Equipment {

    @Id
    private String id;
    private String name;


}
