package expert.os.example.model;


import expert.os.api.Column;
import expert.os.api.Entity;

@Entity("table")
public class Person7 {

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
