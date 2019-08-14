package br.com.nanothings.wildmobile.helper;

public class Utils {
    public static String repeatString(String target, int times) {
        return new String(new char[times]).replace("\0", target);
    }

    public static String brToUsCurrency(String value) {
        return value.replace(".", "")
                .replace(",", ".");
    }
}
