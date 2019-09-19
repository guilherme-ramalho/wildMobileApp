package br.com.nanothings.wildmobile.adapter;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.nanothings.wildmobile.R;
import br.com.nanothings.wildmobile.interfaces.BluetoothDeviceItemManager;
import br.com.nanothings.wildmobile.viewholder.DispositivosBluetoothViewHolder;

public class DispositivosBluetoothAdapter extends RecyclerView.Adapter<DispositivosBluetoothViewHolder> {
    private List<BluetoothDevice> dispositivosPareados;
    private BluetoothDeviceItemManager itemManager;

    public DispositivosBluetoothAdapter(List<BluetoothDevice> dispositivosPareados, BluetoothDeviceItemManager itemManager) {
        this.dispositivosPareados = dispositivosPareados;
        this.itemManager = itemManager;
    }

    @NonNull
    @Override
    public DispositivosBluetoothViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_dispositivo_bluetooth, parent, false);

        return new DispositivosBluetoothViewHolder(view, itemManager);
    }

    @Override
    public void onBindViewHolder(@NonNull DispositivosBluetoothViewHolder holder, int position) {
        BluetoothDevice device = dispositivosPareados.get(position);

        holder.nomeDispositivoTextView.setText(device.getName());
        holder.enderecoDispositivoTextView.setText(device.getAddress());
        holder.tipoDispositivoImageView.setImageResource(
                device.getBluetoothClass().getMajorDeviceClass() == BluetoothClass.Device.Major.IMAGING
                        ? R.drawable.printer
                        : R.drawable.bluetooth
        );
    }

    @Override
    public int getItemCount() {
        return dispositivosPareados.size();
    }
}
