package utils;

public class ObjectUtils {
    public static void checkNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
