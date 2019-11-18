package br.com.nanothings.wildmobile.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.nanothings.wildmobile.R;

public class ListaSorteioViewHolder extends RecyclerView.ViewHolder {
    public TextView dataSorteioTextView, resultadoSorteioTextView;

    public ListaSorteioViewHolder(@NonNull View itemView) {
        super(itemView);

        dataSorteioTextView = itemView.findViewById(R.id.dataSorteioTextView);
        resultadoSorteioTextView = itemView.findViewById(R.id.resultadoSorteioTextView);
    }
}
