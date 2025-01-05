package utils;

public class Utils {
    public static void safePrintln(Object obj) {
        synchronized (System.out) {
            System.out.println(obj);
        }
    }
}
