package br.com.nanothings.wildmobile.helper;

import android.app.AlertDialog;
import android.content.Context;

import java.math.BigDecimal;
import java.text.NumberFormat;

import br.com.nanothings.wildmobile.R;

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

    public static AlertDialog.Builder getDialogBuilder(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.warning)
                .setMessage(message)
                .setCancelable(false);

        return builder;
    }
}
