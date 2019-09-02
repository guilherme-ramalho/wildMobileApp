package br.com.nanothings.wildmobile.helper;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Constants {
    public static final String API_URL = "http://192.168.0.144:8080/";
    /*public static final String API_URL = "http://api.bancaconfianca.com/";*/

    public static final Locale LOCALE_BRAZIL = new Locale("pt", "BR");

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm", Constants.LOCALE_BRAZIL);
}
