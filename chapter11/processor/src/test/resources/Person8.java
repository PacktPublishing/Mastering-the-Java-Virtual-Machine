package org.soujava.example.model;


import org.soujava.medatadata.api.Column;
import org.soujava.medatadata.api.Entity;

@Entity("table")
public class Person8 {

    @Column
    private String name;

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}
