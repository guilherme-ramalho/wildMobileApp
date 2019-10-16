package br.com.nanothings.wildmobile.helper;

public class PrinterHelper {
    public static String gerarTituloComprovante() {
        StringBuilder builder = new StringBuilder();

        String line = Utils.repeatString("#", 32);

        builder.append(line)
                .append("Banca Confiança\n")
                .append(line);

        return builder.toString();
    }
}
