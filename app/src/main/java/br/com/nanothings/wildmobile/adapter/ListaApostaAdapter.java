package br.com.nanothings.wildmobile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.nanothings.wildmobile.R;
import br.com.nanothings.wildmobile.helper.Utils;
import br.com.nanothings.wildmobile.interfaces.ApostaItemManager;
import br.com.nanothings.wildmobile.model.Aposta;
import br.com.nanothings.wildmobile.viewholder.ListaApostaViewHolder;

public class ListaApostaAdapter extends RecyclerView.Adapter<ListaApostaViewHolder> {
    private List<Aposta> listaApostas;
    private ApostaItemManager itemManager;

    public ListaApostaAdapter(List<Aposta> listaApostas, ApostaItemManager itemManager) {
        this.listaApostas = listaApostas;
        this.itemManager = itemManager;
    }

    public void setData(List<Aposta> listaApostas) {
        this.listaApostas = listaApostas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListaApostaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_lista_aposta, parent, false);

        return new ListaApostaViewHolder(view, itemManager);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaApostaViewHolder holder, int position) {
        Aposta aposta = listaApostas.get(position);

        holder.valorApostaTextView.setText(Utils.bigDecimalToStr(aposta.getValorAposta()));
        holder.valorPremioTextView.setText(Utils.bigDecimalToStr(aposta.getValorPremio()));
        holder.dataApostaTextView.setText(aposta.getDataFormatada());
        holder.nomeApostadorTextView.setText(aposta.getNomeApostador());
    }

    @Override
    public int getItemCount() {
        return listaApostas.size();
    }
}
