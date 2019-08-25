package br.com.nanothings.wildmobile.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.nanothings.wildmobile.interfaces.ApostaItemManager;

public class ListaApostaViewHolder extends RecyclerView.ViewHolder {
    private ApostaItemManager itemManager;

    public ListaApostaViewHolder(@NonNull View itemView, ApostaItemManager itemManager) {
        super(itemView);

        this.itemManager = itemManager;
    }
}
