public class ConstantPoolSample {
    private String message = "Hello, Java!"; // String literal stored in the constant pool
    public static void main(String[] args) {
        ConstantPoolSample sample = new ConstantPoolSample();
        System.out.println(sample.message); // Accessing the field with a symbolic reference
    }
}
