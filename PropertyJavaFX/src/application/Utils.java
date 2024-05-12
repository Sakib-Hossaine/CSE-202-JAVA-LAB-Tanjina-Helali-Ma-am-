package application;

public class Utils {
    public static double parseDouble(String value) {
        return value != null && !value.isBlank() ? Double.parseDouble(value) : 0.0;
    }

    public static int parseInt(String value) {
        return value != null && !value.isBlank() ? Integer.parseInt(value) : 0;
    }

    public static boolean isValid(String value) {
        return value != null && !value.isBlank();
    }
}
