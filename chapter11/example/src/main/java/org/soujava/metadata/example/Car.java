package org.soujava.metadata.example;

import org.soujava.medatadata.api.Column;
import org.soujava.medatadata.api.Entity;
import org.soujava.medatadata.api.Id;

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
