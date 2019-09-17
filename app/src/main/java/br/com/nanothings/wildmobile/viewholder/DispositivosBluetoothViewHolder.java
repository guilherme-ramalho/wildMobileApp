package br.com.nanothings.wildmobile.viewholder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.nanothings.wildmobile.R;
import br.com.nanothings.wildmobile.interfaces.BluetoothDeviceItemManager;

public class DispositivosBluetoothViewHolder extends RecyclerView.ViewHolder {
    public TextView nomeDispositivoTextView;
    public LinearLayout dispositivoLayout;
    private BluetoothDeviceItemManager itemManager;

    public DispositivosBluetoothViewHolder(@NonNull View itemView, BluetoothDeviceItemManager itemManager) {
        super(itemView);

        nomeDispositivoTextView = itemView.findViewById(R.id.nomeDispositivoTextView);
        dispositivoLayout = itemView.findViewById(R.id.dispositivoBluetoothLayout);

        this.itemManager = itemManager;

        dispositivoItemClick();
    }

    private void dispositivoItemClick() {
        dispositivoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemManager.bluetoothDeviceItemClick(getAdapterPosition());
            }
        });
    }
}
