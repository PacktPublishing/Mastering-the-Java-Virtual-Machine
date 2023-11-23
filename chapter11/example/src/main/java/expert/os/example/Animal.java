package expert.os.example;

import expert.os.api.Column;
import expert.os.api.Entity;
import expert.os.api.Id;

@Entity("kind")
public class Animal {

    @Id
    private String name;

    @Column
    private String color;

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    void setColor(String color) {
        this.color = color;
    }
}
