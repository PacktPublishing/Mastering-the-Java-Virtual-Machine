package org.soujava.metadata.compiler;

import org.soujava.medatadata.api.Column;
import org.soujava.medatadata.api.Entity;
import org.soujava.medatadata.api.Id;

@Entity("animal")
public class Animal {

    @Id
    private String id;

    @Column
    private String name;

    public Animal(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Animal() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
