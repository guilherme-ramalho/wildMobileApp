package br.com.nanothings.wildmobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

import br.com.nanothings.wildmobile.R;
import br.com.nanothings.wildmobile.adapter.DispositivosBluetoothAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DispositivosBluetoothActivity extends AppCompatActivity {
    @BindView(R.id.recyclerDispositivosBluetooth) RecyclerView recyclerDispositivosBluetooth;
    @BindView(R.id.progressBarDeviceList) ProgressBar progressBarDeviceList;

    private Context context;
    private DispositivosBluetoothAdapter dispositivosBluetoothAdapter;
    private BluetoothAdapter btAdapter;
    private Set<BluetoothDevice> dispositivosPareados;
    private ArrayList<String> listaNomesDispositivos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivos_bluetooth);

        context = this;

        ButterKnife.bind(this);

        getBluetoothDevices();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void getBluetoothDevices() {
        btAdapter = BluetoothAdapter.getDefaultAdapter();

        if (btAdapter != null && btAdapter.isEnabled()) {
            dispositivosPareados = btAdapter.getBondedDevices();

            if (dispositivosPareados.size() > 0) {
                for (BluetoothDevice dispositivo : dispositivosPareados) {
                    listaNomesDispositivos.add(dispositivo.getName());
                }

                setRecyclerDispositivosBluetooth();
            }
        } else {
            Toast.makeText(context, "Nenhum dispositivo encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    private void setRecyclerDispositivosBluetooth() {
        dispositivosBluetoothAdapter = new DispositivosBluetoothAdapter(listaNomesDispositivos);

        recyclerDispositivosBluetooth.setLayoutManager(new LinearLayoutManager(context));
        recyclerDispositivosBluetooth.setHasFixedSize(true);
        recyclerDispositivosBluetooth.addItemDecoration(new DividerItemDecoration(
                recyclerDispositivosBluetooth.getContext(), DividerItemDecoration.VERTICAL
        ));
        recyclerDispositivosBluetooth.setAdapter(dispositivosBluetoothAdapter);
    }
}
