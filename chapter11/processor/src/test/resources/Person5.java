package org.soujava.example.model;


import org.soujava.medatadata.api.Entity;
import org.soujava.medatadata.api.Column;

@Entity("table")
public class Person5 {

    @Column
    private String name;

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}
