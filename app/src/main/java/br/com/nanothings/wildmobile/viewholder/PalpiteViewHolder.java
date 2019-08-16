package br.com.nanothings.wildmobile.viewholder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.nanothings.wildmobile.R;
import br.com.nanothings.wildmobile.interfaces.PalpiteItemManager;

public class PalpiteViewHolder extends RecyclerView.ViewHolder {
    public TextView modalidadeTextView, valorApostaTextView, palpitesTexView,
            cercoTextView, multiplicadorTextView;
    public LinearLayout palpiteLayout;
    private PalpiteItemManager palpiteItemManager;

    public PalpiteViewHolder(@NonNull View itemView, PalpiteItemManager palpiteItemManager) {
        super(itemView);

        modalidadeTextView = itemView.findViewById(R.id.modalidadeTextView);
        valorApostaTextView = itemView.findViewById(R.id.valorApostaTextView);
        palpitesTexView = itemView.findViewById(R.id.palpitesTextView);
        cercoTextView = itemView.findViewById(R.id.cercoTextView);
        palpiteLayout = itemView.findViewById(R.id.palpiteLayout);
        multiplicadorTextView = itemView.findViewById(R.id.multiplicadorTextView);

        this.palpiteItemManager = palpiteItemManager;
    }
}
