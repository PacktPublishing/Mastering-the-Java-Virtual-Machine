package org.soujava.metadata.reflection;


import org.soujava.medatadata.api.Column;
import org.soujava.medatadata.api.Entity;
import org.soujava.medatadata.api.Id;

@Entity("animal")
public class Animal {

    @Id
    private String id;

    @Column("native_name")
    private String name;

    public Animal() {
    }

    public Animal(String id, String name) {
        this.id = id;
        this.name = name;
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
