package br.com.nanothings.wildmobile.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.nanothings.wildmobile.R;
import br.com.nanothings.wildmobile.interfaces.ApostaItemManager;

public class ListaApostaViewHolder extends RecyclerView.ViewHolder {
    private ApostaItemManager itemManager;
    public LinearLayout listaApostaLayout;
    public TextView valorApostaTextView, valorPremioTextView, dataApostaTextView, codigoApostaTextView;
    public ImageView statusImageView;

    public ListaApostaViewHolder(@NonNull View itemView, ApostaItemManager itemManager) {
        super(itemView);

        valorApostaTextView = itemView.findViewById(R.id.valorApostaTextView);
        valorPremioTextView = itemView.findViewById(R.id.valorPremioTextView);
        dataApostaTextView = itemView.findViewById(R.id.dataApostaTextView);
        codigoApostaTextView = itemView.findViewById(R.id.codigoApostaTextView);
        listaApostaLayout = itemView.findViewById(R.id.listaApostaLayout);
        statusImageView = itemView.findViewById(R.id.statusImageView);

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
