package br.com.nanothings.wildmobile.interfaces;

import android.bluetooth.BluetoothSocket;

public interface BluetoothConnectionListener {
    void onConnected(BluetoothSocket socket);

    void onFailed();
}
