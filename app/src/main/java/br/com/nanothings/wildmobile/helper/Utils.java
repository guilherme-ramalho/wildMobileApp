package br.com.nanothings.wildmobile.helper;

import android.app.ProgressDialog;
import android.content.Context;

public class Utils {
    public static String repeatString(String target, int times) {
        return new String(new char[times]).replace("\0", target);
    }
}
