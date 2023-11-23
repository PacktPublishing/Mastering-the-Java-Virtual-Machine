public final class AccessSample {
    private int value;

    public AccessSample(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static void main(String[] args) {
        AccessSample sample = new AccessSample(42);
        System.out.println("Sample Value: " + sample.getValue());
    }
}
