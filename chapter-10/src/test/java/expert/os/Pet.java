package expert.os;

@Entity
public class Pet {

    @Column
    private String name;

    @Column
    private int age;

    @Deprecated
    public Pet() {
    }

    private Pet(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String name() {
        return name;
    }

    public int age() {
        return age;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public static Pet of(String name, int age) {
        //TODO validations
        return new Pet(name, age);
    }
}
