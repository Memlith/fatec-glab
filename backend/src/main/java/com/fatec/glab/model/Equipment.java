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
    public Object getName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
    }
    public void setName(Object name2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setName'");
    }
    public Object getCategory() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCategory'");
    }
    public void setCategory(Object category) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setCategory'");
    }
    public Object getManufacturer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getManufacturer'");
    }
    public void setManufacturer(Object manufacturer) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setManufacturer'");
    }
    public Object getStatus() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getStatus'");
    }
    public void setStatus(Object status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setStatus'");
    }


}
