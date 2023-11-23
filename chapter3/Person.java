public class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public static void main(String[] args) {
        // Creating an instance of Person
        Person person = new Person("John");

        // Accessing and displaying the name attribute
        System.out.println("Original Name: " + person.getName());

        // Changing the name attribute
        person.setName("Alice");

        // Displaying the updated name
        System.out.println("Updated Name: " + person.getName());
    }
}
