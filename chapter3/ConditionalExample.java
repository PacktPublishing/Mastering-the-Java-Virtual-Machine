public class ConditionalExample {

    public static void main(String[] args) {
        int a = 5;
        int b = 3;

        if (a == b) {
            System.out.println("a is equal to b");
        } else {
            System.out.println("a is not equal to b");
        }

        String str1 = "Hello";
        String str2 = "Hello";

        if (str1.equals(str2)) {
            System.out.println("Strings are equal");
        } else {
            System.out.println("Strings are not equal");
        }
    }
}
