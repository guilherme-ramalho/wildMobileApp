package br.com.nanothings.wildmobile.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.nanothings.wildmobile.R;

public class DispositivosBluetoothViewHolder extends RecyclerView.ViewHolder {
    public TextView nomeDispositivoTextView;

    public DispositivosBluetoothViewHolder(@NonNull View itemView) {
        super(itemView);

        nomeDispositivoTextView = itemView.findViewById(R.id.nomeDispositivoTextView);
    }
}
