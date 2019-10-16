package br.com.nanothings.wildmobile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.nanothings.wildmobile.R;
import br.com.nanothings.wildmobile.helper.Utils;
import br.com.nanothings.wildmobile.interfaces.PalpiteItemManager;
import br.com.nanothings.wildmobile.model.Palpite;
import br.com.nanothings.wildmobile.viewholder.PalpiteViewHolder;

public class PalpiteAdapter extends RecyclerView.Adapter<PalpiteViewHolder> {
    private List<Palpite> listaPalpites;
    private PalpiteItemManager palpiteItemManager;

    public PalpiteAdapter(List<Palpite> listaPalpites, PalpiteItemManager palpiteItemManager) {
        this.listaPalpites = listaPalpites;
        this.palpiteItemManager = palpiteItemManager;
    }

    public void setData(List<Palpite> listaPalpites) {
        this.listaPalpites = listaPalpites;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PalpiteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View palpiteView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_palpite, parent, false);

        return new PalpiteViewHolder(palpiteView, palpiteItemManager);
    }

    @Override
    public void onBindViewHolder(@NonNull PalpiteViewHolder holder, int position) {
        Palpite palpite = listaPalpites.get(position);

        holder.palpitesTexView.setText("(" + palpite.getNumerosString() + ")");
        holder.intervalorPremioTextView.setText(palpite.getTextIntervaloPremio());
        holder.valorApostaTextView.setText(Utils.bigDecimalToStr(palpite.getValorAposta()));
        holder.modalidadeTextView.setText(palpite.getTipoPalpite().getNome());
        holder.multiplicadorTextView.setText("(" + palpite.getTipoPalpite().getMultiplicador().intValue() + "x)");
        holder.valorPremioTextView.setText(Utils.bigDecimalToStr(palpite.getValorPremio()));
    }

    @Override
    public int getItemCount() {
        return listaPalpites.size();
    }
}
