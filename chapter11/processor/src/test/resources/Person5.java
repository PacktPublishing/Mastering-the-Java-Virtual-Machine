package org.soujava.example.model;


import expert.os.api.Entity;
import expert.os.api.Column;

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
