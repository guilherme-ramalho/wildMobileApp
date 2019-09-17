package br.com.nanothings.wildmobile.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

import br.com.nanothings.wildmobile.R;
import br.com.nanothings.wildmobile.adapter.DispositivosBluetoothAdapter;
import br.com.nanothings.wildmobile.interfaces.BluetoothDeviceItemManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DispositivosBluetoothActivity extends AppCompatActivity implements BluetoothDeviceItemManager {
    @BindView(R.id.recyclerDispositivosBluetooth) RecyclerView recyclerDispositivosBluetooth;
    @BindView(R.id.semDispositivosTextView) TextView semDispositivosTextView;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(context, "Result", Toast.LENGTH_SHORT).show();
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

                recyclerDispositivosBluetooth.setVisibility(View.VISIBLE);
                semDispositivosTextView.setVisibility(View.GONE);
            }
        } else {
            recyclerDispositivosBluetooth.setVisibility(View.GONE);
            semDispositivosTextView.setVisibility(View.VISIBLE);
        }
    }

    private void setRecyclerDispositivosBluetooth() {
        dispositivosBluetoothAdapter = new DispositivosBluetoothAdapter(listaNomesDispositivos, this);

        recyclerDispositivosBluetooth.setLayoutManager(new LinearLayoutManager(context));
        recyclerDispositivosBluetooth.setHasFixedSize(true);
        recyclerDispositivosBluetooth.addItemDecoration(new DividerItemDecoration(
                recyclerDispositivosBluetooth.getContext(), DividerItemDecoration.VERTICAL
        ));
        recyclerDispositivosBluetooth.setAdapter(dispositivosBluetoothAdapter);
    }

    @Override
    public void bluetoothDeviceItemClick(int position) {
        Toast.makeText(context, "pos " + position , Toast.LENGTH_SHORT).show();
    }
}
