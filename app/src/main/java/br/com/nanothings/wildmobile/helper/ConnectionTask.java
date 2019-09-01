package br.com.nanothings.wildmobile.helper;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.UUID;

import br.com.nanothings.wildmobile.interfaces.BluetoothConnectionListener;

public class ConnectionTask extends AsyncTask<BluetoothDevice, Void, BluetoothSocket> {
    private BluetoothConnectionListener btConnectListener;

    public ConnectionTask(BluetoothConnectionListener btConnectListener) {
        this.btConnectListener = btConnectListener;
    }

    @Override
    public BluetoothSocket doInBackground(BluetoothDevice... bluetoothDevices) {
        BluetoothDevice device = bluetoothDevices[0];
        UUID uuid = device.getUuids()[0].getUuid();
        BluetoothSocket socket = null;
        boolean connected = true;

        try {
            socket = device.createRfcommSocketToServiceRecord(uuid);
            socket.connect();
        } catch (IOException e) {
            try {
                socket = (BluetoothSocket) device.getClass()
                        .getMethod("createRfcommSocket", new Class[]{int.class})
                        .invoke(device, 1);
                socket.connect();
            } catch (Exception e2) {
                connected = false;
            }
        }

        return connected ? socket : null;
    }

    @Override
    protected void onPostExecute(BluetoothSocket bluetoothSocket) {
        if (btConnectListener != null) {
            if (bluetoothSocket != null) btConnectListener.onConnected(bluetoothSocket);
            else btConnectListener.onFailed();
        }
    }
}
