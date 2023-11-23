package org.soujava.example.model;


import org.soujava.medatadata.api.Entity;
import org.soujava.medatadata.api.Column;

import java.util.List;

@Entity("table")
public class Person6 {

    @Column("native")
    private String name;

    @Column
    private List<String> names;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}
