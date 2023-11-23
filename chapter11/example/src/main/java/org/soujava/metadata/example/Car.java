package org.soujava.metadata.example;

import expert.os.api.Column;
import expert.os.api.Entity;
import expert.os.api.Id;

@Entity("car")
public class Car {

    @Id
    private String name;

    @Column
    private String model;

    Car() {
    }


    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    protected String getModel() {
        return model;
    }

    protected void setModel(String model) {
        this.model = model;
    }
}
