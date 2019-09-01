package br.com.nanothings.wildmobile.model;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import br.com.nanothings.wildmobile.R;
import br.com.nanothings.wildmobile.helper.BluetoothPrinter;
import br.com.nanothings.wildmobile.helper.Constants;
import br.com.nanothings.wildmobile.helper.Utils;
import br.com.nanothings.wildmobile.interfaces.PrinterConnectionListener;

public class Aposta implements Serializable {
    private int id;
    private String codigo, status, nomeApostador;
    private BigDecimal valorAposta, valorPremio, valorComissao;
    @SerializedName("dataOriginal")
    private Date data;
    @SerializedName("palpiteAposta")
    private List<Palpite> palpites;

    public Aposta() {
        this.palpites = new ArrayList<>();
        this.valorAposta = BigDecimal.ZERO;
        this.valorPremio = BigDecimal.ZERO;
        this.valorComissao = BigDecimal.ZERO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNomeApostador() {
        return nomeApostador;
    }

    public void setNomeApostador(String nomeApostador) {
        this.nomeApostador = nomeApostador;
    }

    public BigDecimal getValorAposta() {
        return valorAposta;
    }

    public void setValorAposta(BigDecimal valorAposta) {
        this.valorAposta = valorAposta;
    }

    public BigDecimal getValorPremio() {
        return valorPremio;
    }

    public void setValorPremio(BigDecimal valorPremio) {
        this.valorPremio = valorPremio;
    }

    public BigDecimal getValorComissao() {
        return valorComissao;
    }

    public void setValorComissao(BigDecimal valorComissao) {
        this.valorComissao = valorComissao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List<Palpite> getPalpites() {
        return palpites;
    }

    public void setPalpites(List<Palpite> palpites) {
        this.palpites = palpites;
    }

    public void addValorAposta(BigDecimal apostaPalpite) {
        this.valorAposta = this.valorAposta.add(apostaPalpite);
    }

    public void addPremioPalpite(BigDecimal premioPalpite) {
        this.valorPremio = this.valorPremio.add(premioPalpite);
    }

    public String getDataFormatada() {
        SimpleDateFormat dateFormat = Constants.DATE_FORMAT;
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

        return dateFormat.format(this.data);
    }

    private String gerarComprovante() {
        StringBuilder comprovante = new StringBuilder();

        //Header com comprovante
        comprovante.append("Cód. Aposta: " + codigo + "\n")
                .append("Apostador: " + nomeApostador + "\n")
                .append("Valor Apostado: " + Utils.bigDecimalToStr(valorAposta) + "\n")
                .append("Valor Prêmio: " + Utils.bigDecimalToStr(valorPremio) + "\n")
                .append("Data: " + getDataFormatada() + "\n")
                .append(Utils.repeatString("-", 32) + "\n");
        
        //Lista de palpites
        for (Palpite palpite : palpites) {
            comprovante.append("Tipo: " + palpite.getTipoPalpite().getNome())
                    .append("(" + palpite.getNumerosString() + ")\n")
                    .append("Intervalo: " + palpite.getTextIntervaloPremio() + "\n")
                    .append("Valor palpite: " + Utils.bigDecimalToStr(palpite.getValorAposta()) + "\n")
                    .append(Utils.repeatString("-", 32));
        }

        return comprovante.toString();
    }

    public void imprimirComprovante(Context context) {
        try {
            BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
            BluetoothDevice btDevice = btAdapter
                    .getBondedDevices()
                    .iterator()
                    .next();

            final BluetoothPrinter btPrinter = new BluetoothPrinter(btDevice);

            btPrinter.connect(new PrinterConnectionListener() {
                @Override
                public void onConnected() {
                    Toast.makeText(context, R.string.bluetooth_connection_success, Toast.LENGTH_SHORT).show();
                    btPrinter.setAlign(BluetoothPrinter.ALIGN_LEFT);
                    btPrinter.printText(gerarComprovante());
                    btPrinter.addNewLine(6);
                    btPrinter.finish();
                }

                @Override
                public void onFailed() {
                    Toast.makeText(context, R.string.bluetooth_connection_error, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(context, R.string.printer_error, Toast.LENGTH_SHORT).show();
        }
    }
}
