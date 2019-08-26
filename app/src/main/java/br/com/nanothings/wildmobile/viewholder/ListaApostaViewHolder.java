package br.com.nanothings.wildmobile.viewholder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.nanothings.wildmobile.R;
import br.com.nanothings.wildmobile.interfaces.ApostaItemManager;

public class ListaApostaViewHolder extends RecyclerView.ViewHolder {
    private ApostaItemManager itemManager;
    public LinearLayout listaApostaLayout;
    public TextView valorApostaTextView, valorPremioTextView, dataApostaTextView, nomeApostadorTextView;

    public ListaApostaViewHolder(@NonNull View itemView, ApostaItemManager itemManager) {
        super(itemView);

        valorApostaTextView = itemView.findViewById(R.id.valorApostaTextView);
        valorPremioTextView = itemView.findViewById(R.id.valorPremioTextView);
        dataApostaTextView = itemView.findViewById(R.id.dataApostaTextView);
        nomeApostadorTextView = itemView.findViewById(R.id.nomeApostadorTextView);
        listaApostaLayout = itemView.findViewById(R.id.listaApostaLayout);

        this.itemManager = itemManager;

        apostaItemClick();
    }

    private void apostaItemClick() {
        listaApostaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemManager.apostaItemClick(getAdapterPosition());
            }
        });
    }
}
