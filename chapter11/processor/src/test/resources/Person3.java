package expert.os.example.model;


import expert.os.api.Entity;
import expert.os.api.Column;

@Entity("table")
public class Person3 {

    @Column
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}
