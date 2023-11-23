public class MethodCallsExample {

    public static void main(String[] args) {
        int result = performCalculation(5, 3);
        System.out.println("Result of calculation: " + result);
    }

    private static int performCalculation(int a, int b) {
        int sum = add(a, b);
        int product = multiply(a, b);
        return subtract(sum, product);
    }

    private static int add(int a, int b) {
        return a + b;
    }

    private static int multiply(int a, int b) {
        return a * b;
    }

    private static int subtract(int a, int b) {
        return a - b;
    }
}
