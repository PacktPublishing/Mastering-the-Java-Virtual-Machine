public class ValueConversionsExample {
    public static void main(String[] args) {
        // Promotion: Enlargement of Types
        int intValue = 42;
        long longValue = intValue; // Promotion: int to long

        float floatValue = 3.14f;
        double doubleValue = floatValue; // Promotion: float to double

        // Shortening: Considerations for Loss and Overflow
        short shortValue = 32767;
        byte byteValue = (byte) shortValue; // Shortening: short to byte

        double largeDouble = 1.7e308;
        int intFromDouble = (int) largeDouble; // Shortening: double to int

        // Displaying Results
        System.out.println("Promotion Results: " + longValue + ", " + doubleValue);
        System.out.println("Shortening Results: " + byteValue + ", " + intFromDouble);
    }
}
