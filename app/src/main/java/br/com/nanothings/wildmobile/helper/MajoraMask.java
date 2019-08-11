package br.com.nanothings.wildmobile.helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.NumberFormat;
import java.util.Locale;

public class MajoraMask {
    public static void currencyMask(EditText editText) {
        editText.addTextChangedListener(currencyMaskWatcher(editText));
    }

    public static String unmask(String s) {
        return s.replaceAll("[.]", "").replaceAll("[-]", "")
                .replaceAll("[/]", "").replaceAll("[(]", "")
                .replaceAll("[)]", "").replaceAll(" ", "")
                .replaceAll(",", "");
    }

    public static String maskString(String mask, String text) {
        int i = 0;
        String mascara = "";
        for (char m : mask.toCharArray()) {
            if (m != '#') {
                mascara += m;
                continue;
            }
            try {
                mascara += text.charAt(i);
            } catch (Exception e) {
                break;
            }
            i++;
        }

        return mascara;
    }

    private static boolean isASign(char c) {
        if (c == '.' || c == '-' || c == '/' || c == '(' || c == ')' || c == ',' || c == ' ') {
            return true;
        } else {
            return false;
        }
    }

    private static TextWatcher currencyMaskWatcher(final EditText editText) {
        return new TextWatcher() {
            private String current = "";

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    editText.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[R$,.]", "");

                    Double parsed = Double.parseDouble(cleanString);
                    Locale locale = new Locale("pt", "BR");
                    String formatted = NumberFormat.getCurrencyInstance(locale).format((parsed/100));

                    current = formatted.replaceAll("[R$]", "");
                    editText.setText(current);
                    editText.setSelection(editText.length());
                    editText.addTextChangedListener(this);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) { }
        };
    }
}
