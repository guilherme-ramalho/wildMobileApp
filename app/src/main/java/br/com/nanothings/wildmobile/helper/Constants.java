package br.com.nanothings.wildmobile.helper;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Constants {
    /*public static final String API_URL = "http://192.168.1.106:8080/";*/
    public static final String API_URL = "http://api.bancaconfianca.com/";

    public static final Locale LOCALE_BRAZIL = new Locale("pt", "BR");

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm", Constants.LOCALE_BRAZIL);

    public static final int FRAGMENT_INICIO_CODE = 1;
    public static final int FRAGMENT_APOSTAS_CODE = 2;
    public static final int FRAGMENT_APURACAO_CODE = 3;
}
