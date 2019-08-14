package br.com.nanothings.wildmobile.helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.NumberFormat;
import java.util.Locale;

public class MajoraMask {
    private String palpiteMask;
    private TextWatcher palpiteTextWatcher;

    public void setPalpiteMask(String palpiteMask) {
        this.palpiteMask = palpiteMask;
    }

    public String unmask(String s) {
        return s.replaceAll("[.]", "").replaceAll("[-]", "")
                .replaceAll("[/]", "").replaceAll("[(]", "")
                .replaceAll("[)]", "").replaceAll(" ", "")
                .replaceAll(",", "");
    }

    private boolean isASign(char c) {
        if (c == '.' || c == '-' || c == '/' || c == '(' || c == ')' || c == ',' || c == ' ') {
            return true;
        } else {
            return false;
        }
    }

    public void addCurrencyMask(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
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
        });
    }

    public void addPalpiteMask(EditText editText) {
        palpiteTextWatcher = new TextWatcher() {
            boolean isUpdating;
            String old = "";

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = unmask(s.toString());
                String mask = palpiteMask;
                String maskedString = "";

                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                    return;
                }

                int index = 0;
                for (int i = 0; i < mask.length(); i++) {
                    char m = mask.charAt(i);
                    if (m != '#') {
                        if (index == str.length() && str.length() < old.length()) {
                            continue;
                        }
                        maskedString += m;
                        continue;
                    }

                    try {
                        maskedString += str.charAt(index);
                    } catch (Exception e) {
                        break;
                    }

                    index++;
                }

                if (maskedString.length() > 0) {
                    char last_char = maskedString.charAt(maskedString.length() - 1);
                    boolean hadSign = false;
                    while (isASign(last_char) && str.length() == old.length()) {
                        maskedString = maskedString.substring(0, maskedString.length() - 1);
                        last_char = maskedString.charAt(maskedString.length() - 1);
                        hadSign = true;
                    }

                    if (maskedString.length() > 0 && hadSign) {
                        maskedString = maskedString.substring(0, maskedString.length() - 1);
                    }
                }

                isUpdating = true;
                editText.setText(maskedString);
                editText.setSelection(maskedString.length());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void afterTextChanged(Editable s) {}
        };

        editText.addTextChangedListener(palpiteTextWatcher);
    }

    public void removePalpiteTextWatcher(EditText editText) {
        if(palpiteTextWatcher != null) {
            editText.removeTextChangedListener(palpiteTextWatcher);
        }
    }
}
