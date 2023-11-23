package expert.os.example.model;


import expert.os.api.Entity;
import expert.os.api.Column;

@Entity
public class Person2 {

    @Column
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Person2() {}

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}
