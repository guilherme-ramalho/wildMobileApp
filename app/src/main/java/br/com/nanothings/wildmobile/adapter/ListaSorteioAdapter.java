package br.com.nanothings.wildmobile.adapter;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.nanothings.wildmobile.R;
import br.com.nanothings.wildmobile.model.Resultado;
import br.com.nanothings.wildmobile.model.Sorteio;
import br.com.nanothings.wildmobile.viewholder.ListaSorteioViewHolder;

public class ListaSorteioAdapter extends RecyclerView.Adapter<ListaSorteioViewHolder> {
    private List<Sorteio> listaSorteio;

    public ListaSorteioAdapter(List<Sorteio> listaSorteio) {
        this.listaSorteio = listaSorteio;
    }

    public void setData(List<Sorteio> listaResultado) {
        this.listaSorteio = listaResultado;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListaSorteioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_resultado_aposta, parent, false);

        return new ListaSorteioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaSorteioViewHolder holder, int position) {
        Sorteio sorteio = listaSorteio.get(position);

        String stringResultados = "";
        int i = 1;

        //Montando a string
        for (Resultado resultado : sorteio.getResultados()) {
            stringResultados += i + "º " + resultado.getNumero();

            if (i < resultado.getNumero().length() + 1) stringResultados += " - ";

            i++;
        }

        /*SpannableStringBuilder finalSpannable = new SpannableStringBuilder("");
        final ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.BLACK);
        final StyleSpan styleSpan = new StyleSpan(android.graphics.Typeface.BOLD);

        for(String string : stringResultados.split(" - ")) {
            SpannableStringBuilder spannable = new SpannableStringBuilder(string);

            int inicioNumero = string.indexOf(" ") + 1;
            int finalNumero = string.length();

            spannable.setSpan(colorSpan, inicioNumero, finalNumero, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            spannable.setSpan(styleSpan, inicioNumero, finalNumero, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

            finalSpannable.append(spannable.append(" "));
        }*/


        holder.dataSorteioTextView.setText(sorteio.getData());
        holder.resultadoSorteioTextView.setText(stringResultados);
    }

    @Override
    public int getItemCount() {
        return listaSorteio.size();
    }

    private int getStatusImageId(String status) {
        switch (status) {
            case "VNC":
                return R.drawable.venceu;
            case "PRD":
                return R.drawable.perdeu;
            case "CLD":
                return R.drawable.cancelado;
            default:
                return R.drawable.aguardando;
        }
    }
}
