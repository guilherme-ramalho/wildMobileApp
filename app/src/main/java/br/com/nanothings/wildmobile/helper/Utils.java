package br.com.nanothings.wildmobile.helper;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class Utils {
    public static String repeatString(String target, int times) {
        return new String(new char[times]).replace("\0", target);
    }

    public static String brToUsCurrency(String value) {
        return value.replace(".", "")
                .replace(",", ".");
    }

    public static String bigDecimalToStr(BigDecimal bigDecimal) {
        return NumberFormat.getCurrencyInstance(Constants.LOCALE_BRAZIL).format(bigDecimal);
    }
}
