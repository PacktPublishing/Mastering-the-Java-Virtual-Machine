package org.soujava.metadata.example;

import org.soujava.medatadata.api.Column;
import org.soujava.medatadata.api.Entity;
import org.soujava.medatadata.api.Id;

@Entity("kind")
public class Animal {

    @Id
    private String name;

    @Column
    private String color;

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    void setColor(String color) {
        this.color = color;
    }
}
