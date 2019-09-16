package br.com.nanothings.wildmobile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.nanothings.wildmobile.R;
import br.com.nanothings.wildmobile.viewholder.DispositivosBluetoothViewHolder;

public class DispositivosBluetoothAdapter extends RecyclerView.Adapter<DispositivosBluetoothViewHolder> {
    private ArrayList<String> dispositivosPareados;

    public DispositivosBluetoothAdapter(ArrayList<String> dispositivosPareados) {
        this.dispositivosPareados = dispositivosPareados;
    }

    @NonNull
    @Override
    public DispositivosBluetoothViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_dispositivo_bluetooth, parent, false);

        return new DispositivosBluetoothViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DispositivosBluetoothViewHolder holder, int position) {
        String nomeDispositivo = dispositivosPareados.get(position);

        holder.nomeDispositivoTextView.setText(nomeDispositivo);
    }

    @Override
    public int getItemCount() {
        return dispositivosPareados.size();
    }
}
