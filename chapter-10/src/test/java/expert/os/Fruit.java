package expert.os;

@Entity
@Append(key = "type", value = "Fruit")
@Append(key = "category", value = "Natural")
public class Fruit {

    @Column
    private String name;


    public Fruit(String name) {
        this.name = name;
    }

    @Deprecated
    public Fruit() {
    }

    public String name() {
        return name;
    }
}
