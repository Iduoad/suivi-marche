package com.employe.microemploye.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Service {

    @Id
    @GeneratedValue
    private int id ;

    private String label ;
    private String description ;


    public Service() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
