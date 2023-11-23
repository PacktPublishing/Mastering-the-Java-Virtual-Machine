package org.soujava.example.model;

import org.soujava.medatadata.api.Entity;
import org.soujava.medatadata.api.Column;

@Entity("table")
public class Person4 {

    @Column("native")
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}
